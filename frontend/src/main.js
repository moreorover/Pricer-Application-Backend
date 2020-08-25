import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import vuetify from './plugins/vuetify';
import lodash from 'lodash'
import 'nprogress/nprogress.css'

axios.defaults.baseURL = 'http://51.178.240.249:8080/api';

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  lodash,
  render: h => h(App)
}).$mount('#app')
