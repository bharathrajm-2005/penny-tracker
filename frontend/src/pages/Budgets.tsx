import React, { useState, useEffect } from 'react';
import api from '../services/api';
import { Plus, Trash2, AlertTriangle } from 'lucide-react';

const Budgets = () => {
  const [budgets, setBudgets] = useState<any[]>([]);
  const [categories, setCategories] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  
  const currentMonth = new Date().getMonth() + 1;
  const currentYear = new Date().getFullYear();

  const [formData, setFormData] = useState({ amount: '', month: currentMonth, year: currentYear, categoryId: '' });

  useEffect(() => {
    fetchBudgets();
    fetchCategories();
  }, []);

  const fetchBudgets = async () => {
    try {
      const res = await api.get(`/budgets?month=${currentMonth}&year=${currentYear}`);
      setBudgets(res.data);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const fetchCategories = async () => {
    try {
      const res = await api.get('/categories');
      if (res.data.length === 0) {
        const defaults = ['Food & Dining', 'Transportation', 'Entertainment', 'Shopping', 'Utilities', 'Housing'];
        for (const name of defaults) {
          await api.post('/categories', { name, icon: 'default' });
        }
        const updatedRes = await api.get('/categories');
        setCategories(updatedRes.data);
      } else {
        setCategories(res.data);
      }
    } catch (error) {
      console.error(error);
    }
  };

  const handleDelete = async (id: number) => {
    if(window.confirm('Are you sure?')){
      await api.delete(`/budgets/${id}`);
      fetchBudgets();
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const payload = {
        ...formData,
        categoryId: formData.categoryId === '' ? null : formData.categoryId
      };
      await api.post('/budgets', payload);
      setShowModal(false);
      fetchBudgets();
      setFormData({ amount: '', month: currentMonth, year: currentYear, categoryId: '' });
    } catch (error) {
      console.error(error);
    }
  };

  if (loading) return <div>Loading...</div>;

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-xl font-semibold text-gray-800 dark:text-gray-100">Budgets (Current Month)</h2>
        <button onClick={() => setShowModal(true)} className="flex items-center bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition">
          <Plus className="w-5 h-5 mr-2" /> Set Budget
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
        {budgets.map((budget) => {
          const percentUsed = (budget.amountSpent / budget.amount) * 100;
          const isWarning = percentUsed >= 80;
          const isExceeded = percentUsed >= 100;

          return (
            <div key={budget.id} className="bg-white dark:bg-gray-800 p-6 rounded-xl shadow-sm border border-gray-100 dark:border-gray-700 transition-colors">
              <div className="flex justify-between items-start mb-4">
                <div>
                  <h3 className="text-lg font-medium text-gray-900 dark:text-gray-100">
                    {budget.category ? budget.category.name : 'Overall Budget'}
                  </h3>
                  <p className="text-sm text-gray-500 dark:text-gray-400">${budget.amountSpent.toFixed(2)} spent of ${budget.amount.toFixed(2)}</p>
                </div>
                <button onClick={() => handleDelete(budget.id)} className="text-gray-400 hover:text-red-600 dark:hover:text-red-400 transition">
                  <Trash2 className="w-5 h-5" />
                </button>
              </div>

              <div className="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-2.5 mb-2">
                <div 
                  className={`h-2.5 rounded-full ${isExceeded ? 'bg-red-600' : isWarning ? 'bg-yellow-500' : 'bg-green-600'}`} 
                  style={{ width: `${Math.min(percentUsed, 100)}%` }}
                ></div>
              </div>
              
              <div className="flex justify-between items-center mt-2">
                <span className={`text-sm font-medium ${isExceeded ? 'text-red-600 dark:text-red-400' : isWarning ? 'text-yellow-600 dark:text-yellow-400' : 'text-gray-500 dark:text-gray-400'}`}>
                  {percentUsed.toFixed(1)}% Used
                </span>
                {isWarning && !isExceeded && (
                  <span className="flex items-center text-xs text-yellow-600 dark:text-yellow-400 font-medium bg-yellow-50 dark:bg-yellow-900/30 px-2 py-1 rounded">
                    <AlertTriangle className="w-3 h-3 mr-1" /> Near limit
                  </span>
                )}
                {isExceeded && (
                  <span className="flex items-center text-xs text-red-600 dark:text-red-400 font-medium bg-red-50 dark:bg-red-900/30 px-2 py-1 rounded">
                    <AlertTriangle className="w-3 h-3 mr-1" /> Exceeded
                  </span>
                )}
              </div>
            </div>
          );
        })}
        {budgets.length === 0 && (
          <div className="col-span-full py-8 text-center text-gray-500 dark:text-gray-400 bg-white dark:bg-gray-800 rounded-xl shadow-sm border border-gray-100 dark:border-gray-700">
            No budgets set for this month.
          </div>
        )}
      </div>

      {showModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
          <div className="bg-white dark:bg-gray-800 rounded-xl shadow-xl w-full max-w-md overflow-hidden">
            <div className="px-6 py-4 border-b border-gray-200 dark:border-gray-700 flex justify-between items-center">
              <h3 className="text-lg font-medium text-gray-900 dark:text-white">Set New Budget</h3>
              <button onClick={() => setShowModal(false)} className="text-gray-400 hover:text-gray-500 dark:hover:text-gray-300 text-xl">&times;</button>
            </div>
            <form onSubmit={handleSubmit} className="p-6 space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">Amount</label>
                <input type="number" step="0.01" required value={formData.amount} onChange={e => setFormData({...formData, amount: e.target.value})} className="mt-1 block w-full rounded-md border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm px-3 py-2 border" />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">Category (Optional)</label>
                <select value={formData.categoryId} onChange={e => setFormData({...formData, categoryId: e.target.value})} className="mt-1 block w-full rounded-md border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm px-3 py-2 border">
                  <option value="">-- Overall Monthly Budget --</option>
                  {categories.map(cat => <option key={cat.id} value={cat.id}>{cat.name}</option>)}
                </select>
                <p className="text-xs text-gray-500 dark:text-gray-400 mt-1">Leave empty to set an overall budget for the month.</p>
              </div>
              <div className="pt-4 flex justify-end">
                <button type="button" onClick={() => setShowModal(false)} className="bg-white dark:bg-gray-700 py-2 px-4 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm text-sm font-medium text-gray-700 dark:text-gray-200 hover:bg-gray-50 dark:hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 mr-3">Cancel</button>
                <button type="submit" className="bg-blue-600 border border-transparent rounded-md shadow-sm py-2 px-4 inline-flex justify-center text-sm font-medium text-white hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">Save</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Budgets;
