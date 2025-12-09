import httpClient from "../http-common";

const getAll = () => httpClient.get('/employees');

const create = data => httpClient.post("/employees", data);

const get = id => httpClient.get(`/employees/${id}`);

const update = data => httpClient.put(`/employees/${data.id}`, data);

const remove = id => httpClient.delete(`/employees/${id}`);

export default { getAll, create, get, update, remove };
