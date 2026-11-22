import api from "./api";


// ==========================================
// GET ALL SCREENS
// ==========================================

export const getAllScreens = async () => {

    const response = await api.get("/screens");

    return response.data;
};


// ==========================================
// GET SCREEN BY ID
// ==========================================

export const getScreenById = async (id) => {

    const response = await api.get(`/screens/${id}`);

    return response.data;
};


// ==========================================
// GET SCREENS BY THEATER
// ==========================================

export const getScreensByTheater = async (theaterId) => {

    const response = await api.get(
        `/screens/theater/${theaterId}`
    );

    return response.data;
};


// ==========================================
// CREATE SCREEN
// ==========================================

export const createScreen = async (screenData) => {

    const response = await api.post(
        "/screens",
        screenData
    );

    return response.data;
};


// ==========================================
// UPDATE SCREEN
// ==========================================

export const updateScreen = async (
    id,
    screenData
) => {

    const response = await api.put(
        `/screens/${id}`,
        screenData
    );

    return response.data;
};


// ==========================================
// DELETE SCREEN
// ==========================================

export const deleteScreen = async (id) => {

    await api.delete(`/screens/${id}`);
};