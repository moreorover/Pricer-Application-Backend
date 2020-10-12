<template>
  <v-card>
    <v-card-title class="justify-center">{{ deal.item.name }}</v-card-title>
    <v-card-text>
      <LineChart :labels="prices.labels" :values="prices.priceValues" label="Price" :minY="minY"></LineChart>
      <LineChart :labels="prices.labels" :values="prices.deltaValues" label="Delta"></LineChart>
      <LineChart :labels="prices.labels" :values="prices.priceRunningValues" label="Running Price"></LineChart>
      <LineChart :labels="prices.labels" :values="prices.deltaRunningValues" label="Running Delta"></LineChart>
    </v-card-text>
  </v-card>
</template>

<script>
import _ from 'lodash'
import LineChart from '../components/LineChart'

export default {
  name: 'Deal',
  components: { LineChart },
  props: {
    deal: {
      type: Object,
      required: true,
    },
  },
  computed: {
    prices() {
      let pricesSorted = _.orderBy(this.deal.item.prices, 'foundTime', 'asc')

      return {
        labels: pricesSorted.map((x) => x.foundTime.split('T')[0]),
        priceValues: pricesSorted.map((x) => x.price),
        deltaValues: pricesSorted.map((x) => x.delta),
        priceRunningValues: pricesSorted
          .map((x) => x.price)
          .map(((sum) => (value) => (sum += value))(0)),
        deltaRunningValues: pricesSorted
          .map((x) => x.delta)
          .map(((sum) => (value) => (sum += value))(0)),
      }
    },
    minY() {
      let sortedPrices = _.sortBy(this.prices.priceValues)

      return sortedPrices[0] * 0.98
    },
  },
}
</script>

<style scoped>
</style>