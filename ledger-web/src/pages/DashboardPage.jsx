import { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext';
import api from '../services/api';
import { useNavigate } from 'react-router-dom';

export default function DashboardPage() {
    const { user, logout } = useAuth();
    const [account, setAccount] = useState(null);
    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        api.get('/account')
            .then(res => setAccount(res.data))
            .catch(() => setError('Failed to load account'))
            .finally(() => setLoading(false));

        api.get('/transactions')
            .then(res => setTransactions(res.data.slice(0, 3)))
            .catch(() => {});
    }, []);

    return (
        <div className="min-h-screen bg-gray-100 p-8">
            <div className="max-w-xl mx-auto bg-white rounded-2xl shadow p-8">

                <div className="flex justify-between items-center mb-6">
                    <h1 className="text-2xl font-bold text-gray-800">
                        Welcome, {user?.email}
                    </h1>
                    <button
                        onClick={logout}
                        className="text-sm text-red-500 hover:underline"
                    >
                        Logout
                    </button>
                </div>

                {loading && <p className="text-gray-500">Loading account...</p>}
                {error && <p className="text-red-500">{error}</p>}

                {account && (
                    <div className="bg-blue-50 rounded-xl p-6 mb-6">
                        <p className="text-sm text-gray-500 mb-1">Account Number</p>
                        <p className="text-lg font-mono font-semibold">{account.accountNo}</p>
                        <p className="text-sm text-gray-500 mt-4 mb-1">Balance</p>
                        <p className="text-3xl font-bold text-blue-700">
                            ${account.balance.toFixed(2)}
                        </p>
                    </div>
                )}

                <div className="flex gap-4 mb-6">
                    <button
                        onClick={() => navigate('/transfer')}
                        className="flex-1 bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700"
                    >
                        Send Money
                    </button>
                    <button
                        onClick={() => navigate('/history')}
                        className="flex-1 border border-gray-300 text-gray-700 py-2 rounded-lg hover:bg-gray-50"
                    >
                        Transaction History
                    </button>
                </div>

                {transactions.length > 0 && (
                    <div>
                        <p className="text-sm font-semibold text-gray-500 mb-3">
                            Recent Transactions
                        </p>
                        <div className="divide-y divide-gray-100">
                            {transactions.map((tx, index) => (
                                <div key={index} className="flex justify-between items-center py-3">
                                    <div>
                                        <p className="text-sm text-gray-800">
                                            {tx.type === 'DEBIT'
                                                ? `To ${tx.receiverAccount}`
                                                : `From ${tx.senderAccount}`}
                                        </p>
                                        <p className="text-xs text-gray-400 mt-1">
                                            {tx.date} · {tx.time?.substring(0, 5)}
                                        </p>
                                    </div>
                                    <span className={`text-sm font-semibold ${
                                        tx.type === 'DEBIT' ? 'text-red-500' : 'text-green-600'
                                    }`}>
                                        {tx.type === 'DEBIT' ? '-' : '+'}${tx.amount.toFixed(2)}
                                    </span>
                                </div>
                            ))}
                        </div>
                    </div>
                )}

            </div>
        </div>
    );
}