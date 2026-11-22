import { useState } from "react";
import { registerUser } from "../services/userService";
import { useNavigate, Link } from "react-router-dom";

function Register() {

    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        name: "",
        email: "",
        password: "",
        phoneNumber: ""
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const handleChange = (event) => {

        const { name, value } = event.target;

        setFormData({
            ...formData,
            [name]: value
        });

    };

    const handleSubmit = async (event) => {

        event.preventDefault();

        setError("");
        setSuccess("");

        if (
            !formData.name ||
            !formData.email ||
            !formData.password ||
            !formData.phoneNumber
        ) {
            setError("Please fill all fields.");
            return;
        }

        try {

            setLoading(true);

            await registerUser(formData);

            setSuccess("Account created successfully!");

            setFormData({
                name: "",
                email: "",
                password: "",
                phoneNumber: ""
            });

            setTimeout(() => {
                navigate("/login");
            }, 1500);

        } catch (error) {

            console.error("Registration error:", error);

            if (error.response?.data?.message) {
                setError(error.response.data.message);
            } else {
                setError(
                    "Unable to create account. Please try again."
                );
            }

        } finally {

            setLoading(false);

        }

    };

    return (

        <div className="auth-page">

            <div className="auth-container">

                {/* LEFT SIDE */}

                <div className="auth-left">

                    <div className="auth-brand">
                        🎬 Cine<span>Spot</span>
                    </div>

                    <p className="auth-small">
                        WELCOME TO CINESPOT
                    </p>

                    <h1>
                        Your movie
                        <span> experience</span>
                        <br />
                        starts here.
                    </h1>

                    <p className="auth-description">
                        Create your CineSpot account and discover
                        movies, theaters, shows and easy ticket booking.
                    </p>

                </div>


                {/* RIGHT SIDE */}

                <div className="auth-card">

                    <div className="auth-header">

                        <h2>
                            Create Account
                        </h2>

                        <p>
                            Join CineSpot today
                        </p>

                    </div>


                    {error && (

                        <div className="auth-error">
                            {error}
                        </div>

                    )}


                    {success && (

                        <div className="auth-success">
                            {success}
                        </div>

                    )}


                    <form onSubmit={handleSubmit}>

                        {/* NAME */}

                        <div className="form-group">

                            <label>
                                Full Name
                            </label>

                            <input
                                type="text"
                                name="name"
                                placeholder="Enter your name"
                                value={formData.name}
                                onChange={handleChange}
                            />

                        </div>


                        {/* EMAIL */}

                        <div className="form-group">

                            <label>
                                Email
                            </label>

                            <input
                                type="email"
                                name="email"
                                placeholder="Enter your email"
                                value={formData.email}
                                onChange={handleChange}
                            />

                        </div>


                        {/* PHONE */}

                        <div className="form-group">

                            <label>
                                Phone Number
                            </label>

                            <input
                                type="tel"
                                name="phoneNumber"
                                placeholder="Enter phone number"
                                value={formData.phoneNumber}
                                onChange={handleChange}
                            />

                        </div>


                        {/* PASSWORD */}

                        <div className="form-group">

                            <label>
                                Password
                            </label>

                            <input
                                type="password"
                                name="password"
                                placeholder="Create a password"
                                value={formData.password}
                                onChange={handleChange}
                            />

                        </div>


                        {/* BUTTON */}

                        <button
                            type="submit"
                            className="auth-submit"
                            disabled={loading}
                        >

                            {loading
                                ? "Creating Account..."
                                : "Create Account"
                            }

                        </button>

                    </form>


                    <div className="auth-footer">

                        Already have an account?

                        <Link to="/login">
                            Login
                        </Link>

                    </div>

                </div>

            </div>

        </div>

    );
}

export default Register;