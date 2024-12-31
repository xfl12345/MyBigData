package cc.xfl12345.mybigdata.server.common.pojo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Stream;

public class SuperObjectDatabase<T> {
    protected Set<String> excludeFields;

    protected Class<T> objectType;

    protected ClassDeclaredInfo classDeclaredInfo;

    protected Set<T> originItemSet;

    /**
     * T 的字段的名称 -> 该字段的值 -> T
     */
    protected Map<String, ConcurrentHashMap<Object, T>> map;

    public SuperObjectDatabase(T object, Set<String> excludeFields) throws Exception {
        init(object, excludeFields);
    }

    @SuppressWarnings("unchecked")
    protected void init(T object, Set<String> excludeFields) throws Exception {
        this.excludeFields = Collections.unmodifiableSet(excludeFields);
        this.objectType = (Class<T>) object.getClass();
        this.classDeclaredInfo = new ClassDeclaredInfo(object);
        this.originItemSet = new CopyOnWriteArraySet<>();

        // 获取保留字段
        Set<String> mappedFields = new HashSet<>(classDeclaredInfo.declaredFields.keySet());
        mappedFields.removeAll(excludeFields);

        // 初始化 map 空间，并且固化 键 ，只允许获取值。
        HashMap<String, ConcurrentHashMap<Object, T>> tmpMap = new HashMap<>(mappedFields.size());
        for (String fieldName : mappedFields) {
            tmpMap.put(fieldName, new ConcurrentHashMap<>());
        }
        map = Collections.unmodifiableMap(tmpMap);
    }

    public Class<T> getObjectType() {
        return objectType;
    }

    public ClassDeclaredInfo getClassDeclaredInfo() {
        return classDeclaredInfo;
    }

    public ConcurrentHashMap<Object, T> getSecondLevelMap(String fieldName) {
        return map.get(fieldName);
    }

    public T getObject(String fieldName, Object fieldValue) {
        return map.get(fieldName).get(fieldValue);
    }


    public int size() {
        synchronized (this) {
            return originItemSet.size();
        }
    }

    public boolean isEmpty() {
        synchronized (this) {
            return originItemSet.isEmpty();
        }
    }

    public boolean contains(T o) {
        synchronized (this) {
            return originItemSet.contains(o);
        }
    }

    public boolean add(T obj) {
        synchronized (this) {
            boolean result = originItemSet.add(obj);
            if (result) {
                map.keySet().parallelStream().forEach(fieldName -> {
                    PropertyInfo propertyInfo = classDeclaredInfo.getPropertyInfoMap().get(fieldName);
                    Object key = propertyInfo.getPropertyHelper().justGet(obj);
                    if (key != null) {
                        // 获取二级表
                        Map<Object, T> secondMap = map.get(fieldName);
                        secondMap.put(key, obj);
                    }
                });
            }

            return result;
        }
    }

    public boolean remove(T obj) {
        synchronized (this) {
            boolean result = originItemSet.remove(obj);
            if (result) {
                map.keySet().parallelStream().forEach(fieldName -> {
                    // 获取二级表
                    Map<Object, T> secondMap = map.get(fieldName);
                    // 遍历二级表，匹配目标元素，若匹配成功，则删除相应的键
                    for (Object key : new HashSet<>(secondMap.keySet())) {
                        T value = secondMap.get(key);
                        if (value != null && value.equals(obj)) {
                            secondMap.remove(key);
                        }
                    }
                });
            }

            return result;
        }
    }

    public void clear() {
        synchronized (this) {
            originItemSet.clear();
            map.keySet().parallelStream().forEach(fieldName -> map.get(fieldName).clear());
        }
    }

    public Stream<T> stream() {
        return originItemSet.stream();
    }

    public Stream<T> parallelStream() {
        return originItemSet.parallelStream();
    }

    public Set<T> getOriginItemSet() {
        return Collections.unmodifiableSet(originItemSet);
    }
}
