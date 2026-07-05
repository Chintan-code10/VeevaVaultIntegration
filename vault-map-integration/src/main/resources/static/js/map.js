const map = L.map('map').setView([20, 0], 2);

// Base map
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors'
}).addTo(map);

// Countries with coordinates
const countries = [
    {
        name: "India",
        lat: 20.5937,
        lng: 78.9629
    },
    {
        name: "USA",
        lat: 37.0902,
        lng: -95.7129
    },
    {
        name: "Canada",
        lat: 56.1304,
        lng: -106.3468
    },
    {
        name: "Brazil",
        lat: -14.2350,
        lng: -51.9253
    },
    {
        name: "Australia",
        lat: -25.2744,
        lng: 133.7751
    },
    {
        name: "Japan",
        lat: 36.2048,
        lng: 138.2529
    },
    {
        name: "Germany",
        lat: 51.1657,
        lng: 10.4515
    }
];

// Add markers
countries.forEach(country => {
    L.marker([country.lat, country.lng])
        .addTo(map)
        .bindPopup("<b>" + country.name + "</b>");
});