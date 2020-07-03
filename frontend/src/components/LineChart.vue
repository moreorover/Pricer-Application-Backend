<script>
    import {Line} from "vue-chartjs";

    export default {
        extends: Line,
        name: "LineChart",
        props: {
            label: {
                type: String,
                required: true
            },
            labels: {
                type: Array,
                required: false
            },
            values: {
                type: Array,
                required: true
            },
            minY: {
                type: Number,
                required: false
            }
        },
        mounted () {
            this.renderChart({
                labels: this.labels,
                datasets: [
                    {
                        label: this.label,
                        backgroundColor: '#f87979',
                        data: this.values
                    }
                ]
            }, {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    yAxes: [{
                        ticks: {
                            suggestedMin: this.minY
                        }
                    }]
                }
            })
        }
    }
</script>

<style scoped>

</style>