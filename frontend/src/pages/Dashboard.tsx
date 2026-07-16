import { useEffect, useState } from 'react';
import api from '../services/api';
import { PieChart, Pie, Cell, ResponsiveContainer, Tooltip } from 'recharts';
import { ArrowDownRight, DollarSign, Target } from 'lucide-react';

const Dashboard = () => {
  const [data, setData] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      const today = new Date();
      const response = await api.get(`/dashboard?month=${today.getMonth() + 1}&year=${today.getFullYear()}`);
      setData(response.data);
    } catch (error) {
      console.error("Failed to fetch dashboard data", error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="flex justify-center items-center h-full">Loading...</div>;
  }

  const COLORS = ['#3b82f6', '#10b981', '#f59e0b', '#ef4444', '#8b5cf6', '#ec4899', '#6b7280'];
  
  const pieData = data?.categoryWiseExpenses 
    ? Object.keys(data.categoryWiseExpenses).map((key, index) => ({
        name: key,
        value: data.categoryWiseExpenses[key],
        color: COLORS[index % COLORS.length]
      }))
    : [];

  return (
    <div className="space-y-6">
      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <StatCard 
          title="Total Expenses" 
          amount={data?.totalExpenses} 
          icon={<DollarSign className="text-red-500" />} 
          trend="down"
        />
        <StatCard 
          title="This Month" 
          amount={data?.currentMonthExpenses} 
          icon={<ArrowDownRight className="text-red-500" />} 
        />
        <StatCard 
          title="Total Budget" 
          amount={data?.totalBudget} 
          icon={<Target className="text-blue-500" />} 
        />
        <StatCard 
          title="Remaining Budget" 
          amount={data?.remainingBudget} 
          icon={<Wallet className="text-green-500" />} 
          isPositive={data?.remainingBudget > 0}
        />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Charts */}
        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100">
          <h3 className="text-lg font-medium text-gray-900 mb-4">Expense Distribution</h3>
          <div className="h-72">
            <ResponsiveContainer width="100%" height="100%">
              <PieChart>
                <Pie
                  data={pieData}
                  cx="50%"
                  cy="50%"
                  innerRadius={60}
                  outerRadius={100}
                  paddingAngle={5}
                  dataKey="value"
                >
                  {pieData.map((entry: any, index: number) => (
                    <Cell key={`cell-${index}`} fill={entry.color} />
                  ))}
                </Pie>
                <Tooltip formatter={(value) => `$${value}`} />
              </PieChart>
            </ResponsiveContainer>
          </div>
          <div className="flex flex-wrap justify-center gap-4 mt-4">
            {pieData.map((entry: any, index: number) => (
              <div key={index} className="flex items-center text-sm text-gray-600">
                <span className="w-3 h-3 rounded-full mr-2" style={{ backgroundColor: entry.color }}></span>
                {entry.name}
              </div>
            ))}
          </div>
        </div>

        {/* Recent Transactions */}
        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100">
          <h3 className="text-lg font-medium text-gray-900 mb-4">Recent Transactions</h3>
          <div className="space-y-4">
            {data?.recentTransactions?.length === 0 ? (
              <p className="text-gray-500">No recent transactions.</p>
            ) : (
              data?.recentTransactions?.map((tx: any) => (
                <div key={tx.id} className="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
                  <div className="flex items-center">
                    <div className="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center text-blue-600 font-bold">
                      {tx.category?.name?.charAt(0) || '?'}
                    </div>
                    <div className="ml-4">
                      <p className="text-sm font-medium text-gray-900">{tx.description || tx.category?.name}</p>
                      <p className="text-xs text-gray-500">{new Date(tx.expenseDate).toLocaleDateString()}</p>
                    </div>
                  </div>
                  <div className="text-sm font-semibold text-gray-900">
                    ${tx.amount.toFixed(2)}
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

const StatCard = ({ title, amount, icon, isPositive }: any) => {
  return (
    <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 flex items-center">
      <div className="p-4 rounded-full bg-gray-50 mr-4">
        {icon}
      </div>
      <div>
        <p className="text-sm font-medium text-gray-500">{title}</p>
        <p className={`text-2xl font-bold ${isPositive === false ? 'text-red-600' : 'text-gray-900'}`}>
          ${amount?.toFixed(2) || '0.00'}
        </p>
      </div>
    </div>
  );
};

// Simple Wallet icon since we used it in Dashboard layout
const Wallet = (props: any) => (
  <svg {...props} xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M21 12V7H5a2 2 0 0 1 0-4h14v4"/><path d="M3 5v14a2 2 0 0 0 2 2h16v-5"/><path d="M18 12a2 2 0 0 0 0 4h4v-4Z"/></svg>
)

export default Dashboard;
