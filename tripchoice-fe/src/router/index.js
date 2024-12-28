import { createRouter, createWebHistory } from "vue-router";

import { storeToRefs } from "pinia";

import { useMemberStore } from "@/stores/member";

const onlyAuthUser = async (to, from, next) => {
  const memberStore = useMemberStore();
  const { userInfo, isValidToken } = storeToRefs(memberStore);
  const { getUserInfo } = memberStore;

  let token = sessionStorage.getItem("accessToken");

  if (userInfo.value != null && token) {
    await getUserInfo(token);
  }
  if (!isValidToken.value || userInfo.value === null) {
    next({ name: "user-login" });
  } else {
    next();
  }
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: () => import("@/views/TheMainView.vue"),
    },
    {
      path: "/users",
      name: "users",
      component: () => import("@/views/TheUserView.vue"),
      children: [
        {
          path: "join",
          name: "user-join",
          component: () => import("@/components/users/UserJoin.vue"),
        },
        {
          path: "profile/:id",
          name: "user-profile",
          //beforeEnter: onlyAuthUser,
          component: () => import("@/components/users/UserProfile.vue"),
          redirect: { name: "user-profile-info" },
          children: [
            {
              path: "info",
              name: "user-profile-info",
              component: () => import("@/components/users/UserProfileInfo.vue"),
            },
            {
              path: "plans",
              name: "user-profile-plans",
              component: () =>
                import("@/components/users/UserProfilePlans.vue"),
            },
          ],
        },
      ],
    },
    {
      path: "/attractions",
      name: "attractions",
      component: () => import("@/views/TheAttractionView.vue"),
    },
    {
      path: "/hotplaces",
      name: "hotplaces",
      component: () => import("@/views/TheHotPlaceView.vue"),
    },
    {
      path: "/notices",
      name: "notices",
      component: () => import("@/views/TheNoticeView.vue"),
      redirect: { name: "notice-list" },
      children: [
        {
          path: "list",
          name: "notice-list",
          component: () => import("@/components/notices/NoticeList.vue"),
        },
        {
          path: "view/:id",
          name: "notice-view",
          // beforeEnter: onlyAuthUser,
          component: () => import("@/components/notices/NoticeDetail.vue"),
        },
        {
          path: "write",
          name: "notice-write",
          // beforeEnter: onlyAuthUser,
          component: () => import("@/components/notices/NoticeWrite.vue"),
        },
        {
          path: "modify/:id",
          name: "notice-modify",
          // beforeEnter: onlyAuthUser,
          component: () => import("@/components/notices/NoticeModify.vue"),
        },
      ],
    },
    {
      path: "/plans",
      name: "plans",
      component: () => import("@/views/ThePlanView.vue"),
    },
  ],
});

export default router;
