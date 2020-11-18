import pupeteer from 'puppeteer'

export default async function scrapeUrl(url: string) {
  const browser = await pupeteer.launch({
    headless: true,
  })
  const page = await browser.newPage()
  await page.goto(url, { waitUntil: 'networkidle0' })

  let ads = await page.evaluate(() => {
    // let ads = document.querySelectorAll('div[class=product-box]')

    let ads = Array.from(
      document.querySelectorAll('div[class=product-box]').values()
    ).map(el => el.innerHTML)

    return ads
  })

  console.log(ads)
  console.log(ads)
  console.log('Found', ads.length, 'ads!')

  await browser.close()
}
