import React, { useState, useEffect } from 'react';
import api from '../services/api';
import { Plus, Trash2 } from 'lucide-react';

const Expenses = () => {
  const [expenses, setExpenses] = useState<any[]>([]);
  const [categories, setCategories] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [formData, setFormData] = useState({ amount: '', description: '', expenseDate: '', paymentMethod: 'CARD', categoryId: '' });

  useEffect(() => {
    fetchExpenses();
    fetchCategories();
  }, []);

  const fetchExpenses = async () => {
    try {
      const res = await api.get('/expenses?pageSize=100');
      setExpenses(res.data.content);
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
        // Auto-seed default categories for the user so the dropdown is never empty
        const defaults = ['Food & Dining', 'Transportation', 'Entertainment', 'Shopping', 'Utilities', 'Housing'];
        for (const name of defaults) {
          await api.post('/categories', { name, icon: 'default' });
        }
        // Fetch again after seeding
        const updatedRes = await api.get('/categories');
        setCategories(updatedRes.data);
        if (updatedRes.data.length > 0) {
          setFormData(prev => ({ ...prev, categoryId: updatedRes.data[0].id }));
        }
      } else {
        setCategories(res.data);
        setFormData(prev => ({ ...prev, categoryId: res.data[0].id }));
      }
    } catch (error) {
      console.error(error);
    }
  };

  const handleDelete = async (id: number) => {
    if(window.confirm('Are you sure?')){
      await api.delete(`/expenses/${id}`);
      fetchExpenses();
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.post('/expenses', formData);
      setShowModal(false);
      fetchExpenses();
      setFormData({ amount: '', description: '', expenseDate: '', paymentMethod: 'CARD', categoryId: categories[0]?.id || '' });
    } catch (error) {
      console.error(error);
    }
  };

  if (loading) return <div>Loading...</div>;

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-xl font-semibold text-gray-800 dark:text-gray-100">All Expenses</h2>
        <button onClick={() => setShowModal(true)} className="flex items-center bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition">
          <Plus className="w-5 h-5 mr-2" /> Add Expense
        </button>
      </div>

      <div className="bg-white dark:bg-gray-800 shadow-sm rounded-lg overflow-hidden border border-gray-100 dark:border-gray-700 transition-colors">
        <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
          <thead className="bg-gray-50 dark:bg-gray-900">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase tracking-wider">Date</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase tracking-wider">Description</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase tracking-wider">Category</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase tracking-wider">Method</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase tracking-wider">Amount</th>
              <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
            </tr>
          </thead>
          <tbody className="bg-white dark:bg-gray-800 divide-y divide-gray-200 dark:divide-gray-700">
            {expenses.map((expense) => (
              <tr key={expense.id} className="hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors">
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">{expense.expenseDate}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900 dark:text-gray-100">{expense.description}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">
                  <span className="bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200 px-2 py-1 rounded-full text-xs font-medium">{expense.category?.name}</span>
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">{expense.paymentMethod}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100 font-semibold">${expense.amount.toFixed(2)}</td>
                <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                  <button onClick={() => handleDelete(expense.id)} className="text-red-600 hover:text-red-900 ml-4"><Trash2 className="w-5 h-5"/></button>
                </td>
              </tr>
            ))}
            {expenses.length === 0 && (
              <tr>
                <td colSpan={6} className="px-6 py-8 text-center text-gray-500">No expenses found.</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      {showModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
          <div className="bg-white dark:bg-gray-800 rounded-xl shadow-xl w-full max-w-md overflow-hidden">
            <div className="px-6 py-4 border-b border-gray-200 dark:border-gray-700 flex justify-between items-center">
              <h3 className="text-lg font-medium text-gray-900 dark:text-white">Add New Expense</h3>
              <button onClick={() => setShowModal(false)} className="text-gray-400 hover:text-gray-500 dark:hover:text-gray-300 text-xl">&times;</button>
            </div>
            <form onSubmit={handleSubmit} className="p-6 space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">Amount</label>
                <input type="number" step="0.01" required value={formData.amount} onChange={e => setFormData({...formData, amount: e.target.value})} className="mt-1 block w-full rounded-md border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm px-3 py-2 border" />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">Description</label>
                <input type="text" required value={formData.description} onChange={e => setFormData({...formData, description: e.target.value})} className="mt-1 block w-full rounded-md border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm px-3 py-2 border" />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">Date</label>
                <input type="date" required value={formData.expenseDate} onChange={e => setFormData({...formData, expenseDate: e.target.value})} className="mt-1 block w-full rounded-md border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm px-3 py-2 border" />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">Category</label>
                <select required value={formData.categoryId} onChange={e => setFormData({...formData, categoryId: e.target.value})} className="mt-1 block w-full rounded-md border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm px-3 py-2 border bg-white dark:bg-gray-700">
                  {categories.map(cat => <option key={cat.id} value={cat.id}>{cat.name}</option>)}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">Payment Method</label>
                <select required value={formData.paymentMethod} onChange={e => setFormData({...formData, paymentMethod: e.target.value})} className="mt-1 block w-full rounded-md border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm px-3 py-2 border bg-white dark:bg-gray-700">
                  <option value="CARD">Card</option>
                  <option value="CASH">Cash</option>
                  <option value="UPI">UPI</option>
                  <option value="BANK">Bank</option>
                </select>
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

export default Expenses;
