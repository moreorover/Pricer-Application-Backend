import axios from "axios";

export default {
    getById(id) {
        return axios.get("deal/" + id).then(response => response.data);
    }
}