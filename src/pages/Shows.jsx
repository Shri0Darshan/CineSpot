import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import { getAllShows } from "../services/showService";


function Shows() {

    const navigate = useNavigate();

    const { screenId } = useParams();

    const [shows, setShows] = useState([]);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState("");


    // ==========================================
    // LOAD ALL SHOWS
    // ==========================================

    useEffect(() => {

        loadShows();

    }, []);


    const loadShows = async () => {

        try {

            setLoading(true);

            setError("");

            console.log(
                "Calling GET /api/shows"
            );

            const data = await getAllShows();

            console.log(
                "Shows received from backend:",
                data
            );


            if (!Array.isArray(data)) {

                throw new Error(
                    "Backend did not return a list of shows."
                );

            }


            // ==========================================
            // FILTER SHOWS FOR CURRENT SCREEN
            // ==========================================

            const filteredShows = data.filter(
                (show) => {

                    const currentScreenId =
                        Number(screenId);

                    const showScreenId =
                        show.screen?.id ??
                        show.screenId;

                    return (
                        Number(showScreenId) ===
                        currentScreenId
                    );

                }
            );


            console.log(
                "Shows for screen:",
                filteredShows
            );


            setShows(filteredShows);

        } catch (error) {

            console.error(
                "SHOWS ERROR:",
                error
            );


            setError(
                error.response?.data?.message ||
                error.message ||
                "Unable to load shows."
            );

        } finally {

            setLoading(false);

        }

    };


    // ==========================================
    // FORMAT DATE
    // ==========================================

    const formatDate = (dateTime) => {

        if (!dateTime) {
            return "Date not available";
        }

        const date =
            new Date(dateTime);

        if (isNaN(date.getTime())) {
            return dateTime;
        }

        return date.toLocaleDateString(
            "en-IN",
            {
                day: "2-digit",
                month: "short",
                year: "numeric"
            }
        );

    };


    // ==========================================
    // FORMAT TIME
    // ==========================================

    const formatTime = (dateTime) => {

        if (!dateTime) {
            return "--:--";
        }

        const date =
            new Date(dateTime);

        if (isNaN(date.getTime())) {
            return dateTime;
        }

        return date.toLocaleTimeString(
            "en-IN",
            {
                hour: "2-digit",
                minute: "2-digit",
                hour12: true
            }
        );

    };


    // ==========================================
    // GET MOVIE TITLE
    // ==========================================

    const getMovieTitle = (show) => {

        if (show.movie?.title) {
            return show.movie.title;
        }

        if (show.movieTitle) {
            return show.movieTitle;
        }

        return "Movie";
    };


    // ==========================================
    // LOADING SCREEN
    // ==========================================

    if (loading) {

        return (

            <div className="app">

                <div className="page-message">

                    <p className="hero-small">
                        CINESPOT
                    </p>

                    <h2>
                        Loading{" "}
                        <span>
                            Shows...
                        </span>
                    </h2>

                    <p>
                        Fetching available
                        movie shows.
                    </p>

                </div>

            </div>

        );

    }


    // ==========================================
    // ERROR SCREEN
    // ==========================================

    if (error) {

        return (

            <div className="app">

                <nav className="navbar">

                    <div
                        className="logo"
                        onClick={() =>
                            navigate("/movies")
                        }
                    >
                        🎬 Cine<span>Spot</span>
                    </div>


                    <div className="nav-links">

                        <a
                            href="/movies"
                            onClick={(event) => {

                                event.preventDefault();

                                navigate("/movies");

                            }}
                        >
                            Movies
                        </a>


                        <a
                            href="/theaters"
                            onClick={(event) => {

                                event.preventDefault();

                                navigate("/theaters");

                            }}
                        >
                            Theaters
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


                <div className="page-message">

                    <p className="hero-small">
                        ERROR
                    </p>


                    <h2>
                        Unable to{" "}
                        <span>
                            Load Shows
                        </span>
                    </h2>


                    <p>
                        {error}
                    </p>


                    <button
                        className="theater-btn"
                        onClick={loadShows}
                    >
                        Try Again
                    </button>


                    <button
                        className="back-btn"
                        onClick={() =>
                            navigate(-1)
                        }
                    >
                        ← Back
                    </button>

                </div>

            </div>

        );

    }


    return (

        <div className="app">

            {/* ==========================================
                NAVBAR
            ========================================== */}

            <nav className="navbar">

                <div
                    className="logo"
                    onClick={() =>
                        navigate("/movies")
                    }
                >
                    🎬 Cine<span>Spot</span>
                </div>


                <div className="nav-links">

                    <a
                        href="/movies"
                        onClick={(event) => {

                            event.preventDefault();

                            navigate("/movies");

                        }}
                    >
                        Home
                    </a>


                    <a
                        href="/movies"
                        onClick={(event) => {

                            event.preventDefault();

                            navigate("/movies");

                        }}
                    >
                        Movies
                    </a>


                    <a
                        href="/theaters"
                        onClick={(event) => {

                            event.preventDefault();

                            navigate("/theaters");

                        }}
                    >
                        Theaters
                    </a>


                    <a
                        href="/bookings"
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


            {/* ==========================================
                HERO
            ========================================== */}

            <section className="theater-hero">

                <div>

                    <p className="hero-small">
                        CINESPOT SHOWS
                    </p>


                    <h1>
                        Choose Your{" "}
                        <span>
                            Show
                        </span>
                    </h1>


                    <p className="hero-description">
                        Select a showtime and
                        continue to seat selection.
                    </p>

                </div>

            </section>


            {/* ==========================================
                SHOW SECTION
            ========================================== */}

            <section className="theater-section">

                <div className="section-heading">

                    <p>
                        AVAILABLE SHOWS
                    </p>


                    <h2>
                        Movie Showtimes
                    </h2>


                    <span>
                        Select your preferred
                        date and time.
                    </span>

                </div>


                {/* ==========================================
                    NO SHOWS
                ========================================== */}

                {shows.length === 0 && (

                    <div className="page-message">

                        <h3>
                            No Shows Available
                        </h3>


                        <p>
                            There are no shows
                            available for this screen.
                        </p>


                        <button
                            className="back-btn"
                            onClick={() =>
                                navigate(-1)
                            }
                        >
                            ← Back to Screens
                        </button>

                    </div>

                )}


                {/* ==========================================
                    SHOW CARDS
                ========================================== */}

                {shows.length > 0 && (

                    <div className="show-grid">

                        {shows.map((show) => (

                            <div
                                className="show-card"
                                key={show.id}
                            >

                                {/* MOVIE */}

                                <div className="show-movie">

                                    <div className="show-icon">
                                        🎬
                                    </div>


                                    <div>

                                        <p className="theater-label">
                                            MOVIE
                                        </p>


                                        <h3>
                                            {getMovieTitle(
                                                show
                                            )}
                                        </h3>

                                    </div>

                                </div>


                                {/* SCREEN */}

                                <div className="show-screen">

                                    🎥

                                    <span>
                                        Screen{" "}
                                        {show.screen?.id ||
                                            show.screenId ||
                                            screenId}
                                    </span>

                                </div>


                                {/* DATE */}

                                <div className="show-date">

                                    📅

                                    <span>
                                        {formatDate(
                                            show.startTime
                                        )}
                                    </span>

                                </div>


                                {/* TIME */}

                                <div className="show-time">

                                    <div>

                                        <small>
                                            START
                                        </small>


                                        <strong>
                                            {formatTime(
                                                show.startTime
                                            )}
                                        </strong>

                                    </div>


                                    <span>
                                        →
                                    </span>


                                    <div>

                                        <small>
                                            END
                                        </small>


                                        <strong>
                                            {formatTime(
                                                show.endTime
                                            )}
                                        </strong>

                                    </div>

                                </div>


                                {/* SELECT SEATS */}

                                <button
                                    className="book-btn"
                                    onClick={() =>
                                        navigate(
                                            `/shows/${show.id}/seats`
                                        )
                                    }
                                >
                                    Select Seats →
                                </button>

                            </div>

                        ))}

                    </div>

                )}


                <button
                    className="back-btn"
                    onClick={() =>
                        navigate(-1)
                    }
                >
                    ← Back to Screens
                </button>

            </section>


            {/* ==========================================
                FOOTER
            ========================================== */}

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


export default Shows;