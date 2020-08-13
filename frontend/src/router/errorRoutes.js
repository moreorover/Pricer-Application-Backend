export default [
    {
        path: '*',
        name: 'Not Found',
        component: () => import(/* webpackChunkName: "about" */ '../views/NotFound'),
    }
]