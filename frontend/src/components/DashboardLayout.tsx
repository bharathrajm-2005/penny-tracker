
import { Outlet, Link, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { LayoutDashboard, Receipt, PiggyBank, LogOut, Wallet, Moon, Sun } from 'lucide-react';
import { useState, useEffect } from 'react';

const DashboardLayout = () => {
  const { logout } = useAuth();
  const location = useLocation();
  const [isDark, setIsDark] = useState(() => {
    return localStorage.getItem('theme') === 'dark' || 
      (!localStorage.getItem('theme') && window.matchMedia('(prefers-color-scheme: dark)').matches);
  });

  useEffect(() => {
    if (isDark) {
      document.documentElement.classList.add('dark');
      localStorage.setItem('theme', 'dark');
    } else {
      document.documentElement.classList.remove('dark');
      localStorage.setItem('theme', 'light');
    }
  }, [isDark]);

  const navItems = [
    { path: '/', label: 'Dashboard', icon: LayoutDashboard },
    { path: '/expenses', label: 'Expenses', icon: Receipt },
    { path: '/budgets', label: 'Budgets', icon: PiggyBank },
  ];

  return (
    <div className="flex h-screen bg-gray-50 dark:bg-gray-900 transition-colors">
      {/* Sidebar */}
      <div className="w-64 bg-white dark:bg-gray-800 border-r border-gray-200 dark:border-gray-700 flex flex-col transition-colors">
        <div className="h-16 flex items-center px-6 border-b border-gray-200 dark:border-gray-700 transition-colors">
          <Wallet className="h-8 w-8 text-blue-600 mr-2" />
          <span className="text-xl font-bold text-gray-900 dark:text-white transition-colors">Smart Expense</span>
        </div>
        <nav className="flex-1 px-4 py-6 space-y-2">
          {navItems.map((item) => {
            const isActive = location.pathname === item.path;
            const Icon = item.icon;
            return (
              <Link
                key={item.path}
                to={item.path}
                className={`flex items-center px-4 py-3 text-sm font-medium rounded-lg transition-colors ${
                  isActive
                    ? 'bg-blue-50 text-blue-700 dark:bg-blue-900/50 dark:text-blue-400'
                    : 'text-gray-600 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-700/50 hover:text-gray-900 dark:hover:text-white'
                }`}
              >
                <Icon className={`mr-3 h-5 w-5 ${isActive ? 'text-blue-700 dark:text-blue-400' : 'text-gray-400 dark:text-gray-500'}`} />
                {item.label}
              </Link>
            );
          })}
        </nav>
        <div className="p-4 border-t border-gray-200 dark:border-gray-700 transition-colors">
          <button
            onClick={logout}
            className="flex items-center w-full px-4 py-3 text-sm font-medium text-gray-600 dark:text-gray-300 rounded-lg hover:bg-gray-50 dark:hover:bg-gray-700/50 hover:text-gray-900 dark:hover:text-white transition-colors"
          >
            <LogOut className="mr-3 h-5 w-5 text-gray-400 dark:text-gray-500" />
            Sign out
          </button>
        </div>
      </div>

      {/* Main content */}
      <div className="flex-1 flex flex-col overflow-hidden">
        <header className="h-16 bg-white dark:bg-gray-800 border-b border-gray-200 dark:border-gray-700 flex items-center justify-between px-8 transition-colors">
          <h1 className="text-2xl font-semibold text-gray-900 dark:text-white transition-colors">
            {navItems.find(i => i.path === location.pathname)?.label || 'Overview'}
          </h1>
          <button
            onClick={() => setIsDark(!isDark)}
            className="p-2 rounded-full text-gray-500 hover:bg-gray-100 dark:text-gray-400 dark:hover:bg-gray-700 transition-colors"
            title="Toggle dark mode"
          >
            {isDark ? <Sun className="w-5 h-5" /> : <Moon className="w-5 h-5" />}
          </button>
        </header>
        <main className="flex-1 overflow-auto p-8">
          <Outlet />
        </main>
      </div>
    </div>
  );
};

export default DashboardLayout;
