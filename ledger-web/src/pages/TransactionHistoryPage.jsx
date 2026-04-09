import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';

export default function TransactionHistoryPage() {
    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        api.get('/transactions')
            .then(res => setTransactions(res.data))
            .catch(() => setError('Failed to load transactions'))
            .finally(() => setLoading(false));
    }, []);

    return (
        <div className="min-h-screen bg-gray-100 p-8">
            <div className="max-w-2xl mx-auto bg-white rounded-2xl shadow p-8">

                <div className="flex justify-between items-center mb-6">
                    <h1 className="text-2xl font-bold text-gray-800">Transaction History</h1>
                    <button
                        onClick={() => navigate('/dashboard')}
                        className="text-sm text-blue-600 hover:underline"
                    >
                        Back to Dashboard
                    </button>
                </div>

                {loading && <p className="text-gray-500">Loading transactions...</p>}
                {error && <p className="text-red-500">{error}</p>}

                {!loading && transactions.length === 0 && (
                    <p className="text-gray-400 text-center py-8">No transactions yet.</p>
                )}

                <div className="divide-y divide-gray-100">
                    {transactions.map((tx, index) => (
                        <div key={index} className="flex justify-between items-center py-4">
                            <div>
                                <p className="text-sm font-medium text-gray-800">
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
        </div>
    );
}