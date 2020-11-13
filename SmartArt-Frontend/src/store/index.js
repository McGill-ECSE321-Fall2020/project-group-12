import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        activeUser: '',
        activeUserType: ''
    },
    mutations: {
        setActiveUser(state, payload) {
            state.activeUser = payload
        },
        setActiveUserType(state, payload) {
            state.activeUserType = payload
        }
    },
    actions: {
        setActiveUser({ commit }, payload) {
            commit('setActiveUser', payload)
        },
        setActiveUserType({ commit }, payload) {
            commit('setActiveUserType', payload)
        }
    },
    modules: {},
    getters: {
        getActiveUser(state) {
            return state.activeUser
        },
        getActiveUserType(state) {
            return state.activeUserType
        }
    }
})