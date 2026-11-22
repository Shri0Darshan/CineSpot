import api from "./api";


// GET ALL THEATERS
export const getAllTheaters = async () => {

    const response = await api.get("/theaters");

    return response.data;
};


// GET THEATER BY ID
export const getTheaterById = async (id) => {

    const response = await api.get(`/theaters/${id}`);

    return response.data;
};


// GET THEATERS BY CITY
export const getTheatersByCity = async (city) => {

    const response = await api.get(
        `/theaters/city/${city}`
    );

    return response.data;
};


// CREATE THEATER
export const createTheater = async (theaterData) => {

    const response = await api.post(
        "/theaters",
        theaterData
    );

    return response.data;
};


// UPDATE THEATER
export const updateTheater = async (
    id,
    theaterData
) => {

    const response = await api.put(
        `/theaters/${id}`,
        theaterData
    );

    return response.data;
};


// DELETE THEATER
export const deleteTheater = async (id) => {

    await api.delete(`/theaters/${id}`);
};