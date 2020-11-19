import { PuppeteerScraper } from './PuppeteerScraper'
import { AdSelectors } from './scraper'

const cwSelectors: AdSelectors = {
  ads: 'div[class=product-box]',
  title: 'h3[class=product-name]',
  url: 'h3[class=product-name] > a',
  upc: 'p[class=product-model-no]',
  price: 'p[class=product-price] > span',
  img: 'div[class=product-img-box] > a > img',
}

function parseItems(adSelectors: AdSelectors) {
  let adsHtmlArray = Array.from(
    document.querySelectorAll(adSelectors.ads).values()
  ).map(el => {
    return {
      title: el.querySelector(adSelectors.title)?.innerText,
      url: el.querySelector(adSelectors.url)?.getAttribute('href'),
      price: parseFloat(
        el.querySelector(adSelectors.price)?.innerText.replace(/[^\d.-]/g, '')
      ),
      upc: 'CW_' + el.querySelector(adSelectors.upc)?.innerText.split(' ')[1],
      img: el.querySelector(adSelectors.img)?.getAttribute('src'),
      foundAt: Date.now(),
    }
  })

  return adsHtmlArray
}

export default async function scrapeUrl(url: string) {
  const p = new PuppeteerScraper()
  await p.init(true)
  await p.fetchUrl(url)

  let ads = await p.page.evaluate(parseItems, cwSelectors)

  console.log('ads--', ads[0])
  console.log('ads--', 'Found', ads.length, 'ads!')

  p.closeBrowser()
}
