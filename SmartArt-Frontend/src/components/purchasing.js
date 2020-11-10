import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function ArtistDto (email, name, password, gallery) {
    this.email = email
    this.name = name
    this.password = password
    this.gallery = gallery
  }
  function AdministratorDto (email, name, password, gallery) {
    this.email = email
    this.name = name
    this.password = password
    this.gallery = gallery
  }
  function BuyerDto (email, name, password, gallery) {
    this.email = email
    this.name = name
    this.password = password
    this.gallery = gallery
  }

  
  function GalleryDto (name, city, commission) {
    this.name = name
    this.city = city
    this.commission = commission
  }

  export default {
  name: 'smartart',
  data () {
    return {
      galleries: [],
      newGallery: '',
      errorGallery: '',
      response: []
    }
  },
  //...
  created: function () {
    // Initializing galleries from backend
   ​AXIOS.get('/galleries')
   ​.then(response => {
     ​// JSON responses are automatically parsed.
     ​this.galleries = response.data
   ​})
   ​.catch(e => {
     ​this.errorGallery = e
   ​})
  },

  methods: {
    createArtist: function (email, name, password, gallery) {
      // Create a new artist and add it to the list of people
      var a = new ArtistDto(email, name, password, gallery)
      this.artists.push(a)
      // Reset the name field for new people
      this.newArtist = ''
    },

    createGallery: function (name){
     AXIOS.post('/gallery/create/'.concat(name), {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          this.galleries.push(response.data)
          this.errorGallery = ''
          this.newGallery = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorPerson = errorMsg
        })
      }
  }
}

