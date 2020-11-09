import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

//preceding lines make calls to backend services using the hosts and ports in config folder

//constructor for "person" object
function PersonDto (name) {
  this.name = name
  this.events = []
}

//event constructor
function EventDto (name, date, start, end) {
  this.name = name
  this.eventDate = date
  this.startTime = start
  this.endTime = end
}

//adding data variables to smartart component declaration, including events array to keep track of events
export default {
  name: 'smartart',
  data() {
      //omtrpdice object newEvent in frontend data with four properties, initial values below
      newEvent: {
             name: '',
             eventDate: '2017-12-08',
             startTime: '09:00',
             endTime: '11:00'
           },
           errorEvent: '',
      return {
        persons: [],
        events: [],
        // ... other data members
      }
    },
  //...
}

//initialization function
created: function () {
    // Initializing persons from backend
    AXIOS.get('/persons')
    .then(response => {
      // JSON responses are automatically parsed.
      this.persons = response.data
    })
    .catch(e => {
      this.errorPerson = e
    })
    // Initializing events
      AXIOS.get('/events').then(response => {this.events = response.data}).catch(e => {this.errorEvent = e});



//event handling method
methods: {
    createPerson: function (personName) {
          AXIOS.post('/persons/'.concat(personName), {}, {})
            .then(response => {
            // JSON responses are automatically parsed.
              this.persons.push(response.data)
              this.errorPerson = ''
              this.newPerson = ''
            })
            .catch(e => {
              var errorMsg = e.response.data.message
              console.log(errorMsg)
              this.errorPerson = errorMsg
            })
        }

    //register person to event
    registerEvent: function (personName, eventName) {
         var indexEv = this.events.map(x => x.name).indexOf(eventName)
         var indexPart = this.persons.map(x => x.name).indexOf(personName)
         var person = this.persons[indexPart]
         var event = this.events[indexEv]
         AXIOS.post('/register', {},
           {params: {
             person: person.name,
             event: event.name}})
         .then(response => {
           // Update appropriate DTO collections
           person.events.push(event)
           this.selectedPerson = ''
           this.selectedEvent = ''
           this.errorRegistration = ''
         })
         .catch(e => {
           var errorMsg = e
           console.log(errorMsg)
           this.errorRegistration = errorMsg
         })
       }
}
