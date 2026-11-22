import {
    BrowserRouter,
    Routes,
    Route,
    Navigate
} from "react-router-dom";

import Movies from "./pages/Movies";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Theaters from "./pages/Theaters";
import Screens from "./pages/Screens";
import Shows from "./pages/Shows";
import ShowSeats from "./pages/ShowSeats";
import MovieShows from "./pages/MovieShows";
import Profile from "./pages/Profile";

function App() {

    return (

        <BrowserRouter>

            <Routes>

                <Route
                    path="/"
                    element={
                        <Navigate to="/movies" />
                    }
                />


                <Route
                    path="/movies"
                    element={<Movies />}
                />


                <Route
                    path="/theaters"
                    element={<Theaters />}
                />


                <Route
                    path="/theaters/:theaterId/screens"
                    element={<Screens />}
                />

                <Route
                    path="/screens/:screenId/shows"
                    element={<Shows />}
                />


                <Route
                    path="/login"
                    element={<Login />}
                />


                <Route
                    path="/register"
                    element={<Register />}
                />


                <Route
                    path="*"
                    element={
                        <Navigate to="/movies" />
                    }
                />

                     <Route
                    path="/shows/:showId/seats"
                    element={<ShowSeats />}
                />

                <Route
                    path="/movies/:movieId/shows"
                    element={<MovieShows />}
                />

                <Route path="/profile" element={<Profile />} />

            </Routes>

        </BrowserRouter>

    );
}


export default App;