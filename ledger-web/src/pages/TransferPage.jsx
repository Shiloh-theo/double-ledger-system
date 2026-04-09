import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';

export default function TransferPage() {
    const [debitAccount, setDebitAccount] = useState('');
    const [formData, setFormData] = useState({
        creditAccount: '',
        amount: ''
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        api.get('/account')
            .then(res => setDebitAccount(res.data.accountNo))
            .catch(() => setError('Failed to load your account details.'));
    }, []);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');
        setSuccess('');

        try {
            const payload = {
                debitAccount,
                creditAccount: formData.creditAccount,
                amount: parseFloat(formData.amount)
            };
            await api.post('/transfer', payload);
            setSuccess('Transfer successful!');
            setTimeout(() => navigate('/dashboard'), 1500);
        } catch (err) {
            const msg = err.response?.data;
            setError(msg || 'Transfer failed. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-gray-100 flex items-center justify-center p-8">
            <div className="bg-white rounded-2xl shadow p-8 w-full max-w-md">

                <div className="flex justify-between items-center mb-6">
                    <h1 className="text-2xl font-bold text-gray-800">Send Money</h1>
                    <button
                        onClick={() => navigate('/dashboard')}
                        className="text-sm text-blue-600 hover:underline"
                    >
                        Back
                    </button>
                </div>

                {debitAccount && (
                    <div className="bg-gray-50 rounded-lg px-4 py-3 mb-4">
                        <p className="text-xs text-gray-400">Sending from</p>
                        <p className="text-sm font-mono font-semibold text-gray-700">
                            {debitAccount}
                        </p>
                    </div>
                )}

                {error && (
                    <p className="text-red-500 text-sm mb-4 bg-red-50 p-3 rounded-lg">
                        {error}
                    </p>
                )}
                {success && (
                    <p className="text-green-600 text-sm mb-4 bg-green-50 p-3 rounded-lg">
                        {success}
                    </p>
                )}

                <div className="space-y-4">
                    <div>
                        <label className="text-sm text-gray-500 mb-1 block">
                            Recipient Account Number
                        </label>
                        <input
                            type="text"
                            name="creditAccount"
                            placeholder="Recipient account number"
                            value={formData.creditAccount}
                            onChange={handleChange}
                            className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
                        />
                    </div>

                    <div>
                        <label className="text-sm text-gray-500 mb-1 block">
                            Amount
                        </label>
                        <input
                            type="number"
                            name="amount"
                            placeholder="0.00"
                            value={formData.amount}
                            onChange={handleChange}
                            min="0"
                            step="0.01"
                            className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
                        />
                    </div>

                    <button
                        onClick={handleSubmit}
                        disabled={loading || !debitAccount}
                        className="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition disabled:opacity-50"
                    >
                        {loading ? 'Sending...' : 'Send Money'}
                    </button>
                </div>

            </div>
        </div>
    );
}