import { PuppeteerScraper } from './PuppeteerScraper'

let parseItems = function (document: Document) {
  console.log(typeof document)
  let adsHtmlArray = Array.from(
    document.querySelectorAll('div[class=product-box]').values()
  ).map(el => {
    return {
      title: el.querySelector('h3[class=product-name]')?.innerText,
      url: el.querySelector('h3[class=product-name] > a')?.getAttribute('href'),
      price: parseFloat(
        el
          .querySelector('p[class=product-price] > span')
          ?.innerText.replace(/[^\d.-]/g, '')
      ),
      upc:
        'CW_' +
        el.querySelector('p[class=product-model-no]')?.innerText.split(' ')[1],
      img: el
        .querySelector('div[class=product-img-box] > a > img')
        ?.getAttribute('src'),
      foundAt: Date.now(),
    }
  })

  return adsHtmlArray
}

let parseElement = function (el: Element) {
  console.log('logging element', el)
  return {
    title: el.querySelector('h3[class=product-name]')?.innerText,
    url: el.querySelector('h3[class=product-name] > a')?.getAttribute('href'),
    price: parseFloat(
      el
        .querySelector('p[class=product-price] > span')
        ?.innerText.replace(/[^\d.-]/g, '')
    ),
    upc:
      'CW_' +
      el.querySelector('p[class=product-model-no]')?.innerText.split(' ')[1],
    img: el
      .querySelector('div[class=product-img-box] > a > img')
      ?.getAttribute('src'),
    foundAt: Date.now(),
  }
}

export default async function scrapeUrl(url: string) {
  const p = new PuppeteerScraper()
  await p.init()
  await p.fetchUrl(url)
  await p.page.exposeFunction('parseItems', parseItems)
  await p.page.exposeFunction('parseElement', parseElement)

  let ads = await p.page.evaluate(() => {
    let adsHtmlArray = Array.from(
      document.querySelectorAll('div[class=product-box]').values()
    ).map(parseElement)

    return adsHtmlArray
  })

  let ads2 = await p.page.evaluate(() => {
    let adsHtmlArray = Array.from(
      document.querySelectorAll('div[class=product-box]').values()
    ).map(el => {
      return {
        title: el.querySelector('h3[class=product-name]')?.innerText,
        url: el
          .querySelector('h3[class=product-name] > a')
          ?.getAttribute('href'),
        price: parseFloat(
          el
            .querySelector('p[class=product-price] > span')
            ?.innerText.replace(/[^\d.-]/g, '')
        ),
        upc:
          'CW_' +
          el
            .querySelector('p[class=product-model-no]')
            ?.innerText.split(' ')[1],
        img: el
          .querySelector('div[class=product-img-box] > a > img')
          ?.getAttribute('src'),
        foundAt: Date.now(),
      }
    })

    return adsHtmlArray
  })

  console.log('ads', ads)
  console.log('ads', 'Found', ads.length, 'ads!')

  console.log('ads2', ads2)
  console.log('ads2', 'Found', ads.length, 'ads!')

  p.closeBrowser()
}
