import api from "./api";

// ==========================================
// GET ALL SHOWS
// ==========================================

export const getAllShows = async () => {
    const response = await api.get("/shows");
    return response.data;
};


// ==========================================
// GET SHOW BY ID
// ==========================================

export const getShowById = async (id) => {
    const response = await api.get(`/shows/${id}`);
    return response.data;
};


// ==========================================
// CREATE SHOW
// ==========================================

export const createShow = async (showData) => {
    const response = await api.post(
        "/shows",
        showData
    );

    return response.data;
};


// ==========================================
// UPDATE SHOW
// ==========================================

export const updateShow = async (id, showData) => {
    const response = await api.put(
        `/shows/${id}`,
        showData
    );

    return response.data;
};


// ==========================================
// DELETE SHOW
// ==========================================

export const deleteShow = async (id) => {
    await api.delete(`/shows/${id}`);
};


 // GET SHOWS BY MOVIE ID
export const getShowsByMovieId = async (movieId) => {
    const response = await api.get(`/shows/movie/${movieId}`);
    return response.data;
};
