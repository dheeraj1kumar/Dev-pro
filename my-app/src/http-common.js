const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api/v1';

import axios from "axios";

export default axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});
