import axios from "axios";

const API_URL = "http://localhost:8082/api/users";

/*
 * Create a new user
 */
export const registerUser = async (userData) => {
    const response = await axios.post(API_URL, userData);
    return response.data;
};

/*
 * Login user
 */
export const loginUser = async (loginData) => {
    const response = await axios.post(`${API_URL}/login`, loginData);
    return response.data;
};

/*
 * Get all users
 */
export const getAllUsers = async () => {
    const response = await axios.get(API_URL);
    return response.data;
};

/*
 * Get user by ID
 */
export const getUserById = async (id) => {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
};

/*
 * Update user
 */
export const updateUser = async (id, userData) => {
    const response = await axios.put(`${API_URL}/${id}`, userData);
    return response.data;
};

/*
 * Delete user
 */
export const deleteUser = async (id) => {
    const response = await axios.delete(`${API_URL}/${id}`);
    return response.data;
};