<template>
    <v-card>
        <v-card-title class="justify-center">{{ deal.item.name }}</v-card-title>
        <v-card-text>
            <LineChart :labels="prices.labels" :values="prices.priceValues" label="Price"></LineChart>
            <LineChart :labels="prices.labels" :values="prices.deltaValues" label="Delta"></LineChart>
        </v-card-text>
    </v-card>
</template>

<script>
    import _ from 'lodash';
    import LineChart from "../components/LineChart";

    export default {
        name: "Deal",
        components: { LineChart },
        props: {
            deal: {
                type: Object,
                required: true
            }
        },
        computed: {
            prices() {
                let pricesSorted = _.orderBy(this.deal.item.prices, 'foundTime', 'asc')

                return {
                    labels: pricesSorted.map(x => x.foundTime.split("T")[0]),
                    priceValues: pricesSorted.map(x => x.price),
                    deltaValues: pricesSorted.map(x => x.delta)
                }
            }
        }
    }
</script>

<style scoped>

</style>