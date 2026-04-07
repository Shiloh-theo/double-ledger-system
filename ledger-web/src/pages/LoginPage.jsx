import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../services/api'
import { useAuth } from '../context/AuthContext'

function LoginPage() {
    const navigate = useNavigate()
    const { login } = useAuth()
    const [formData, setFormData] = useState({ email: '', password: '' })
    const [error, setError] = useState(null)
    const [loading, setLoading] = useState(false)

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value })
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        setLoading(true)
        setError(null)

        try {
            const response = await api.post('/login', formData)
            login(formData.email, response.data)
            navigate('/dashboard')
        } catch (err) {
            setError('Invalid email or password.')
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className="min-h-screen bg-gray-100 flex items-center justify-center">
            <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
                <h2 className="text-2xl font-bold text-center text-blue-600 mb-6">Welcome Back</h2>

                {error && <p className="text-red-500 text-sm mb-4">{error}</p>}

                <div className="space-y-4">
                    <input
                        type="email"
                        name="email"
                        placeholder="Email"
                        value={formData.email}
                        onChange={handleChange}
                        className="w-full border border-gray-300 rounded px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
                    />
                    <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        value={formData.password}
                        onChange={handleChange}
                        className="w-full border border-gray-300 rounded px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
                    />
                    <button
                        onClick={handleSubmit}
                        disabled={loading}
                        className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition disabled:opacity-50"
                    >
                        {loading ? 'Logging in...' : 'Login'}
                    </button>
                </div>

                <p className="text-center text-sm text-gray-500 mt-4">
                    Don't have an account?{' '}
                    <span
                        onClick={() => navigate('/register')}
                        className="text-blue-600 cursor-pointer hover:underline"
                    >
                        Register
                    </span>
                </p>
            </div>
        </div>
    )
}

export default LoginPage