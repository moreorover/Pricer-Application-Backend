import DealsApi from "../api/DealsApi";

export default [
    {
        path: '/deal/:id',
        name: 'Deal Details',
        component: () => import(/* webpackChunkName: "about" */ '../views/Deal'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            DealsApi
                .getById(routeTo.params.id)
                .then(response => {
                    routeTo.params.deal = response
                    next()
                })
                .catch(error => {
                    console.log(error)
                    // if (error.response && error.response.status === 404) {
                    //     next({ name: '404', params: { resource: 'event' } })
                    // } else {
                    //     next({ name: 'network-issue' })
                    // }
                })
        }
    }
]