import { createConnection } from 'typeorm'
import express from 'express'
import path from 'path'
import { Store } from './entities/Store'
import scrapeUrl from './scraper/CreationWatches'

const main = async () => {
  await createConnection({
    type: 'mysql',
    host: '',
    database: '',
    username: '',
    password: '',
    logging: true,
    synchronize: true,
    entities: [path.join(__dirname, './entities/*')],
  })

  const store = await Store.findOne(1)

  const urlToScrape = store!.urls[0].url

  console.log(urlToScrape)

  store!.urls
    .filter(url => url.statusId === 1)
    .forEach(url => scrapeUrl(url.url))

  const app = express()

  app.listen(4000, () => {
    console.log('server started on localhost:4000')
  })
}

main()
