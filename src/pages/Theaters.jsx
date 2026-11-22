import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import {
    getAllTheaters,
    getTheatersByCity
} from "../services/theaterService";


function Theaters() {

    const navigate = useNavigate();

    const [theaters, setTheaters] = useState([]);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState("");

    const [city, setCity] = useState("");


    // ===============================
    // LOAD ALL THEATERS
    // ===============================

    useEffect(() => {

        loadTheaters();

    }, []);


    const loadTheaters = async () => {

        try {

            setLoading(true);

            setError("");

            const data = await getAllTheaters();

            setTheaters(
                Array.isArray(data) ? data : []
            );

        } catch (error) {

            console.error(
                "Theater Error:",
                error
            );

            setError(
                error.response?.data?.message ||
                "Unable to load theaters."
            );

        } finally {

            setLoading(false);

        }
    };


    // ===============================
    // SEARCH THEATER
    // ===============================

    const searchTheaters = async () => {

        if (!city.trim()) {

            loadTheaters();

            return;
        }


        try {

            setLoading(true);

            setError("");

            const data =
                await getTheatersByCity(
                    city.trim()
                );

            setTheaters(
                Array.isArray(data)
                    ? data
                    : []
            );

        } catch (error) {

            console.error(
                "Search Error:",
                error
            );

            setError(
                error.response?.data?.message ||
                "Unable to search theaters."
            );

        } finally {

            setLoading(false);

        }
    };


    return (

        <div className="app">


            {/* =================================
                NAVBAR
            ================================= */}

            <nav className="navbar">

                <div
                    className="logo"
                    onClick={() =>
                        navigate("/movies")
                    }
                    style={{
                        cursor: "pointer"
                    }}
                >
                    🎬 Cine<span>Spot</span>
                </div>


                <div className="nav-links">

                    <a
                        href="#"
                        onClick={(event) => {

                            event.preventDefault();

                            navigate("/movies");

                        }}
                    >
                        Home
                    </a>


                    <a
                        href="#"
                        onClick={(event) => {

                            event.preventDefault();

                            navigate("/movies");

                        }}
                    >
                        Movies
                    </a>


                    <a
                        href="#"
                        className="active-link"
                        onClick={(event) =>
                            event.preventDefault()
                        }
                    >
                        Theaters
                    </a>


                    <a
                        href="#"
                        onClick={(event) => {

                            event.preventDefault();

                            navigate("/bookings");

                        }}
                    >
                        My Bookings
                    </a>

                </div>


                <button
                    className="login-btn"
                    onClick={() =>
                        navigate("/login")
                    }
                >
                    Login
                </button>

            </nav>



            {/* =================================
                HERO
            ================================= */}

            <section className="theater-hero">

                <div>

                    <p className="hero-small">
                        FIND YOUR CINEMA
                    </p>


                    <h1>

                        Choose your

                        <span>
                            Theater
                        </span>

                    </h1>


                    <p className="hero-description">

                        Find your favorite cinema,
                        explore available screens,
                        and choose your movie show.

                    </p>

                </div>

            </section>



            {/* =================================
                THEATER SECTION
            ================================= */}

            <section className="theater-section">


                <div className="section-heading">

                    <p>
                        CINESPOT CINEMAS
                    </p>


                    <h2>
                        Available Theaters
                    </h2>


                    <span>
                        Find theaters near you.
                    </span>

                </div>



                {/* SEARCH */}

                <div className="theater-search">

                    <input
                        type="text"
                        placeholder="Search theater by city..."
                        value={city}
                        onChange={(event) =>
                            setCity(
                                event.target.value
                            )
                        }
                        onKeyDown={(event) => {

                            if (
                                event.key === "Enter"
                            ) {

                                searchTheaters();

                            }

                        }}
                    />


                    <button
                        onClick={
                            searchTheaters
                        }
                    >
                        Search
                    </button>


                    {city && (

                        <button
                            className="clear-btn"
                            onClick={() => {

                                setCity("");

                                loadTheaters();

                            }}
                        >
                            Clear
                        </button>

                    )}

                </div>



                {/* =================================
                    LOADING
                ================================= */}

                {loading && (

                    <div className="page-message">

                        <h3>
                            Loading theaters...
                        </h3>

                    </div>

                )}



                {/* =================================
                    ERROR
                ================================= */}

                {!loading && error && (

                    <div className="auth-error">

                        {error}

                    </div>

                )}



                {/* =================================
                    NO DATA
                ================================= */}

                {!loading &&
                    !error &&
                    theaters.length === 0 && (

                    <div className="page-message">

                        <h3>
                            No theaters found
                        </h3>

                        <p>
                            Try searching another city.
                        </p>

                    </div>

                )}



                {/* =================================
                    THEATER CARDS
                ================================= */}

                {!loading &&
                    !error &&
                    theaters.length > 0 && (

                    <div className="theater-grid">

                        {theaters.map(
                            (theater) => (

                            <div
                                className="theater-card"
                                key={theater.id}
                            >

                                <div className="theater-icon">
                                    🎭
                                </div>


                                <div className="theater-info">

                                    <p className="theater-label">
                                        CINESPOT CINEMA
                                    </p>


                                    <h3>
                                        {theater.name}
                                    </h3>


                                    <p>
                                        📍{" "}
                                        {theater.address ||
                                            "Address unavailable"}
                                    </p>


                                    <p>
                                        🏙️{" "}
                                        {theater.city ||
                                            "City unavailable"}
                                    </p>


                                    <button
                                        className="theater-btn"
                                        onClick={() =>
                                            navigate(
                                                `/theaters/${theater.id}/screens`
                                            )
                                        }
                                    >
                                        View Screens
                                    </button>

                                </div>

                            </div>

                        ))}

                    </div>

                )}

            </section>



            {/* =================================
                FOOTER
            ================================= */}

            <footer className="footer">

                <div className="footer-logo">
                    🎬 CineSpot
                </div>


                <div>
                    © 2026 CineSpot.
                    All rights reserved.
                </div>


                <div className="footer-links">

                    <span>
                        Privacy
                    </span>

                    <span>
                        Terms
                    </span>

                    <span>
                        Contact
                    </span>

                </div>

            </footer>

        </div>
    );
}


export default Theaters;