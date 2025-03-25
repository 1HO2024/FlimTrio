import { create } from "zustand";
import { persist } from "zustand/middleware";

const useAuthStore = create(
  persist(
    (set) => ({
      isLoggedIn: false,
      user: null,
      login: (userInfo) => {
        set({ isLoggedIn: true, user: userInfo });
      },
      logout: () => {
        set({ isLoggedIn: false, user: null });

        localStorage.removeItem("token");
        localStorage.removeItem("auth-storage");
      },
    }),
    {
      name: "auth-storage",
      getStorage: () => localStorage,
    }
  )
);

export default useAuthStore;
