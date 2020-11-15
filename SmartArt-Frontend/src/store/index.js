import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        activeUser: '',
        activeUserType: '',
        activePosting: '',
        activePurchase: ''
    },
    mutations: {
        setActiveUser(state, payload) {
            state.activeUser = payload
        },
        setActiveUserType(state, payload) {
            state.activeUserType = payload
        },
        setActivePosting(state, payload) {
            state.activePosting = payload
        },
        setActivePurchase(state, payload) {
            state.activePurchase = payload
        }
    },
    actions: {
        setActiveUser({ commit }, payload) {
            commit('setActiveUser', payload)
        },
        setActiveUserType({ commit }, payload) {
            commit('setActiveUserType', payload)
        },
        setActivePosting({ commit }, payload) {
            commit('setActivePosting', payload)
        },
        setActivePurchase({ commit }, payload) {
            commit('setActivePurchase', payload)
        }
    },
    modules: {},
    getters: {
        getActiveUser(state) {
            return state.activeUser
        },
        getActiveUserType(state) {
            return state.activeUserType
        },
        getActivePosting(state) {
            return state.activePosting
        },
        getActivePurchase(state) {
            return state.activePurchase
        }
    }
})