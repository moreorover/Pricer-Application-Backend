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
            }, {responsive: true, maintainAspectRatio: false})
        }
    }
</script>

<style scoped>

</style>