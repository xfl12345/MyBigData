export function getImageUrl(name: string) {
  return new URL(`../../static/pic/${name}`, import.meta.url).href;
}
