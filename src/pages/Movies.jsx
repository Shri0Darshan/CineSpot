import { useEffect, useState } from "react";
import { getAllMovies } from "../services/movieService";
import { useNavigate } from "react-router-dom";

function Movies() {
    const navigate = useNavigate();


    const [movies, setMovies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadMovies = async () => {
            try {
                setLoading(true);
                setError("");

                const data = await getAllMovies();

                console.log("Movies from backend:", data);

                if (Array.isArray(data)) {
                    setMovies(data);
                } else {
                    setMovies([]);
                    setError("Invalid movie data received from backend.");
                }
            } catch (err) {
                console.error("Failed to load movies:", err);

                if (err.response) {
                    setError(
                        err.response.data?.message ||
                        `Backend error: ${err.response.status}`
                    );
                } else if (err.request) {
                    setError(
                        "Cannot connect to Spring Boot backend. Make sure backend is running."
                    );
                } else {
                    setError(
                        err.message || "Unable to load movies."
                    );
                }
            } finally {
                setLoading(false);
            }
        };

        loadMovies();
    }, []);

    const scrollToMovies = () => {
        document
            .getElementById("movies")
            ?.scrollIntoView({
                behavior: "smooth"
            });
    };

    if (loading) {
        return (
            <div className="app">
                <section className="hero">
                    <div className="hero-content">
                        <p className="hero-small">
                            CINESPOT
                        </p>

                        <h1>
                            Loading <span>Movies...</span>
                        </h1>

                        <p className="hero-description">
                            Please wait while we fetch movies from CineSpot.
                        </p>
                    </div>
                </section>
            </div>
        );
    }

    if (error) {
        return (
            <div className="app">
                <section className="hero">
                    <div className="hero-content">
                        <p className="hero-small">
                            ERROR
                        </p>

                        <h1>
                            Unable to <span>Load Movies</span>
                        </h1>

                        <p className="hero-description">
                            {error}
                        </p>

                        <button
                            className="explore-btn"
                            onClick={() => window.location.reload()}
                        >
                            Try Again
                        </button>
                    </div>
                </section>
            </div>
        );
    }

    return (
        <div className="app">

            {/* ================= NAVBAR ================= */}

            <nav className="navbar">

                <div className="logo">
                    🎬 Cine<span>Spot</span>
                </div>

                <div className="nav-links">

                    <a href="#home">
                        Home
                    </a>

                    <a href="#movies">
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

                    <a href="#bookings">
                        My Bookings
                    </a>

                </div>

                <button
    className="login-btn"
    onClick={() => navigate("/login")}
>
    Login
</button>

            </nav>


            {/* ================= HERO ================= */}

            <section
                className="hero"
                id="home"
            >

                <div className="hero-content">

                    <p className="hero-small">
                        WELCOME TO CINESPOT
                    </p>

                    <h1>
                        Your next{" "}
                        <span>
                            movie night
                        </span>{" "}
                        starts here.
                    </h1>

                    <p className="hero-description">
                        Discover the latest movies, choose your
                        favorite theater, select your seats and
                        book your tickets in just a few clicks.
                    </p>

                    <button
                        className="explore-btn"
                        onClick={scrollToMovies}
                    >
                        Explore Movies
                    </button>

                </div>


                {/* Hero Decoration */}

                <div className="hero-decoration">

                    <div className="circle circle-one"></div>

                    <div className="circle circle-two"></div>

                    <div className="movie-icon">
                        🎬
                    </div>

                </div>

            </section>


            {/* ================= MOVIES ================= */}

            <section
                className="movies-section"
                id="movies"
            >

                <div className="section-heading">

                    <p>
                        NOW SHOWING
                    </p>

                    <h2>
                        Movies
                    </h2>

                    <span>
                        Choose your movie and start your experience.
                    </span>

                </div>


                {movies.length === 0 ? (

                    <div className="no-movies">

                        <h3>
                            No movies available.
                        </h3>

                        <p>
                            Add movies from your Spring Boot backend.
                        </p>

                    </div>

                ) : (

                    <div className="movie-grid">

                        {movies.map((movie) => (

                            <div
                                className="movie-card"
                                key={movie.id}
                            >

                                {/* ================= POSTER ================= */}

                                <div className="movie-poster">

                                    {movie.poster ? (

                                        <img
                                            src={movie.poster}
                                            alt={movie.title}
                                            className="movie-poster-image"
                                            onError={(event) => {
                                                event.currentTarget.style.display =
                                                    "none";
                                            }}
                                        />

                                    ) : (

                                        <div className="poster-placeholder">

                                            <div className="poster-icon">
                                                🎬
                                            </div>

                                            <span>
                                                {movie.title}
                                            </span>

                                        </div>

                                    )}


                                    <div className="movie-genre">
                                        {movie.genre || "Movie"}
                                    </div>

                                </div>


                                {/* ================= INFORMATION ================= */}

                                <div className="movie-info">

                                    <h3>
                                        {movie.title}
                                    </h3>

                                    <p className="movie-description">
                                        {movie.description ||
                                            "No description available."}
                                    </p>


                                    <div className="movie-details">

                                        <span>
                                            ⏱{" "}
                                            {movie.durationMinutes
                                                ? `${movie.durationMinutes} min`
                                                : "Duration unavailable"}
                                        </span>

                                        <span>
                                            🌐{" "}
                                            {movie.language ||
                                                "Language unavailable"}
                                        </span>

                                    </div>


                                    <div className="movie-bottom">

                                        <div>

                                            <p className="price-label">
                                                Ticket Price
                                            </p>

                                            <div className="movie-price">
                                                ₹{movie.price ?? 0}
                                            </div>

                                        </div>


                                        
                                                
                                        <button
                                            type="button"
                                            className="book-btn"
                                            onClick={() => {
                                                console.log("Book Now clicked:", movie.id);
                                                navigate(`/movies/${movie.id}/shows`);
                                            }}
                                        >
                                            Book Now
                                        </button>


                                    </div>

                                </div>

                            </div>

                        ))}

                    </div>

                )}

            </section>


            {/* ================= FOOTER ================= */}

            <footer className="footer">

                <div className="footer-logo">
                    🎬 CineSpot
                </div>

                <div>
                    © 2026 CineSpot. All rights reserved.
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

export default Movies;