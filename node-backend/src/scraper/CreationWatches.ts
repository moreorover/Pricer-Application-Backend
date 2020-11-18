import { PuppeteerScraper } from './PuppeteerScraper'

let parseItems = function (document: Document) {
  console.log('Inside parseItems function')
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

export default async function scrapeUrl(url: string) {
  const p = new PuppeteerScraper()
  await p.init()
  await p.fetchUrl(url)

  let ads = await p.page.evaluate(() => {
    let doc: Document = document
    return parseItems(doc)
  })

  // let ads = await p.page.evaluate(() => {
  //   let adsHtmlArray = Array.from(
  //     document.querySelectorAll('div[class=product-box]').values()
  //   ).map(el => {
  //     return {
  //       title: el.querySelector('h3[class=product-name]')?.innerText,
  //       url: el
  //         .querySelector('h3[class=product-name] > a')
  //         ?.getAttribute('href'),
  //       price: parseFloat(
  //         el
  //           .querySelector('p[class=product-price] > span')
  //           ?.innerText.replace(/[^\d.-]/g, '')
  //       ),
  //       upc:
  //         'CW_' +
  //         el
  //           .querySelector('p[class=product-model-no]')
  //           ?.innerText.split(' ')[1],
  //       img: el
  //         .querySelector('div[class=product-img-box] > a > img')
  //         ?.getAttribute('src'),
  //       foundAt: Date.now(),
  //     }
  //   })

  //   return adsHtmlArray
  // })

  console.log(ads)
  console.log('Found', ads.length, 'ads!')

  p.closeBrowser()
}
