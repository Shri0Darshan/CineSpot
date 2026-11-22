import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import {
    getScreensByTheater
} from "../services/screenService";


function Screens() {

    const navigate = useNavigate();

    const { theaterId } = useParams();


    const [screens, setScreens] = useState([]);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState("");


    // ==========================================
    // LOAD SCREENS
    // ==========================================

    useEffect(() => {

        loadScreens();

    }, [theaterId]);


    const loadScreens = async () => {

        try {

            setLoading(true);

            setError("");


            const data =
                await getScreensByTheater(
                    theaterId
                );


            setScreens(
                Array.isArray(data)
                    ? data
                    : []
            );

        } catch (error) {

            console.error(
                "Screen Error:",
                error
            );


            setError(
                error.response?.data?.message ||
                "Unable to load screens."
            );

        } finally {

            setLoading(false);

        }
    };


    // ==========================================
    // LOADING
    // ==========================================

    if (loading) {

        return (

            <div className="app">

                <div className="page-message">

                    <h2>
                        Loading Screens...
                    </h2>

                    <p>
                        Please wait.
                    </p>

                </div>

            </div>

        );

    }


    // ==========================================
    // ERROR
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
                        style={{
                            cursor: "pointer"
                        }}
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
                            href="/theaters"
                            onClick={(event) => {

                                event.preventDefault();

                                navigate("/theaters");

                            }}
                        >
                            Theaters
                        </a>

                    </div>

                </nav>


                <div className="page-message">

                    <h2>
                        Unable to Load Screens
                    </h2>

                    <p>
                        {error}
                    </p>


                    <button
                        className="theater-btn"
                        onClick={loadScreens}
                    >
                        Try Again
                    </button>


                    <button
                        className="back-btn"
                        onClick={() =>
                            navigate("/theaters")
                        }
                    >
                        ← Back to Theaters
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
                    style={{
                        cursor: "pointer"
                    }}
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
                        href="/movies#movies"
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
                HEADER
            ========================================== */}

            <section className="theater-hero">

                <div>

                    <p className="hero-small">
                        CINESPOT CINEMA
                    </p>


                    <h1>
                        Select a{" "}
                        <span>
                            Screen
                        </span>
                    </h1>


                    <p className="hero-description">
                        Choose your preferred screen
                        to see available movie shows.
                    </p>

                </div>

            </section>


            {/* ==========================================
                SCREEN SECTION
            ========================================== */}

            <section className="theater-section">


                <div className="section-heading">

                    <p>
                        AVAILABLE SCREENS
                    </p>


                    <h2>
                        Choose Your Screen
                    </h2>


                    <span>
                        Select a screen to see its shows.
                    </span>

                </div>


                {/* ==========================================
                    NO SCREENS
                ========================================== */}

                {screens.length === 0 && (

                    <div className="page-message">

                        <h3>
                            No Screens Available
                        </h3>


                        <p>
                            This theater currently
                            has no screens.
                        </p>


                        <button
                            className="theater-btn"
                            onClick={() =>
                                navigate("/theaters")
                            }
                        >
                            ← Back to Theaters
                        </button>

                    </div>

                )}


                {/* ==========================================
                    SCREEN CARDS
                ========================================== */}

                {screens.length > 0 && (

                    <div className="theater-grid">

                        {screens.map(
                            (screen) => (

                            <div
                                className="theater-card"
                                key={screen.id}
                            >


                                {/* ICON */}

                                <div className="theater-icon">
                                    🎥
                                </div>


                                {/* SCREEN INFORMATION */}

                                <div className="theater-info">

                                    <p className="theater-label">
                                        CINESPOT SCREEN
                                    </p>


                                    <h3>
                                        {screen.name ||
                                            `Screen ${screen.id}`}
                                    </h3>


                                    <p>
                                        🪑 Total Seats:{" "}
                                        {screen.totalSeats}
                                    </p>


                                    <p>
                                        🎬 Screen ID:{" "}
                                        {screen.id}
                                    </p>


                                    <button
                                        className="theater-btn"
                                        onClick={() =>
                                            navigate(
                                                `/screens/${screen.id}/shows`
                                            )
                                        }
                                    >
                                        View Shows →
                                    </button>

                                </div>

                            </div>

                        ))}

                    </div>

                )}


                {/* BACK BUTTON */}

                <button
                    className="back-btn"
                    onClick={() =>
                        navigate("/theaters")
                    }
                >
                    ← Back to Theaters
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


export default Screens;