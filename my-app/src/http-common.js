import axios from "axios";

const API_URL = process.env.REACT_APP_API_URL || "http://backend:8080/api/v1";

export default axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json"
  }
});
