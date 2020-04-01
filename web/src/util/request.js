import axios from 'axios'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
NProgress.configure({
    showSpinner:false,
    ease:'ease',
    speed:500
})
const request = axios.create({

    baseURL: ' http://localhost:6969',
    timeout: 12000,
    headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
    }
})
export default request
request.interceptors.request.use(config => {
    // Do something before request is sent
    NProgress.start()
    return config;
}, error => {
    // Do something with request error
    return Promise.reject(error);
});
request.interceptors.response.use(response => {
    NProgress.done()
    // Do something before response is sent
    return response;
}, error => {
    NProgress.done()
    // Do something with response error
    return Promise.reject(error);
});