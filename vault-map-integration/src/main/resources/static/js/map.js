/**
 * Study Map JavaScript Module
 * Handles Leaflet map functionality for displaying study sites with status colors
 */
var StudyMap = (function() {

    var map;
    var siteMarkers = [];
    var countryMarkers = [];
    var currentStudyData = null;

    // Leaflet colored marker icons
    var markerIcons = {
        '#008000': createColoredIcon('#008000'), // Green
        '#FF0000': createColoredIcon('#FF0000'), // Red
        '#FFA500': createColoredIcon('#FFA500'), // Orange
        '#2E8B57': createColoredIcon('#2E8B57'), // Sea Green
        '#808080': createColoredIcon('#808080'), // Gray
        '#4169E1': createColoredIcon('#4169E1'), // Royal Blue
        '#FFD700': createColoredIcon('#FFD700'), // Gold
        '#8B4513': createColoredIcon('#8B4513'), // Saddle Brown
        '#DC143C': createColoredIcon('#DC143C'), // Crimson
        '#9400D3': createColoredIcon('#9400D3')  // Violet
    };

    /**
     * Initialize the map and event handlers
     */
    function init() {
        console.log('Initializing StudyMap...');

        // Initialize Leaflet map
        initializeMap();

        // Setup event handlers
        setupEventHandlers();

        console.log('StudyMap initialized successfully');
    }

    /**
     * Initialize the Leaflet map
     */
    function initializeMap() {
        // Create map centered on world view
        map = L.map('map').setView([20, 0], 2);

        // Add OpenStreetMap tile layer
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '© <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
            maxZoom: 18
        }).addTo(map);

        // Add scale control
        L.control.scale().addTo(map);

        console.log('Leaflet map initialized');
    }

    /**
     * Setup event handlers for UI controls
     */
    function setupEventHandlers() {
        // Load map button
        document.getElementById('loadMapBtn').addEventListener('click', function() {
            var studyId = document.getElementById('studySelect').value;
            if (studyId) {
                loadStudyData(studyId);
            } else {
                showError('Please select a study first.');
            }
        });

        // Study select change
        document.getElementById('studySelect').addEventListener('change', function() {
            var studyId = this.value;
            if (studyId) {
                loadStudyData(studyId);
            }
        });
        
        
        // Refresh button
        document.getElementById('refreshBtn').addEventListener('click', function() {
            location.reload();
        });
        console.log('Event handlers setup complete');
    }

    /**
     * Create a colored marker icon
     */
    function createColoredIcon(color) {
        return L.divIcon({
            className: 'custom-marker',
            html: '<div style="background-color: ' + color + '; width: 25px; height: 25px; border-radius: 50%; border: 3px solid white; box-shadow: 0 2px 4px rgba(0,0,0,0.3);"></div>',
            iconSize: [25, 25],
            iconAnchor: [12, 12],
            popupAnchor: [0, -12]
        });
    }

    /**
     * Create a country marker icon
     */
    function createCountryIcon(color,name) {
		console.log(color)
        return L.divIcon({
			//#007bff
            className: 'country-marker',
            html:`
        <div style="display: flex; flex-direction: column; align-items: center;">
            <div style="
                font-size: 12px;
                font-weight: bold;
                color: black;
                margin-bottom: 2px;
                text-align: center;
                white-space: nowrap;
            ">
                ${name}
            </div>
            <div style="
                background-color:${color};
                width: 30px;
                height: 30px;
                border-radius: 15%;
                border: 3px solid white;
                box-shadow: 0 2px 6px rgba(0,0,0,0.4);
            "></div>
        </div>
    `,
            iconSize: [30, 30],
            iconAnchor: [15, 15],
            popupAnchor: [0, -15]
        });
    }


  /**
     * Render map data (sites and countries)
     */
    function renderMapData(data) {
        console.log('Rendering map data...');

        // Clear existing markers
        clearMarkers();

        // Add site markers
        if (data.sites && data.sites.length > 0) {
            addSiteMarkers(data.sites);
        }

        // Add country markers
        if (data.sites && data.sites.length > 0) {
            addCountryMarkers(data.sites);
        }

        // Fit map bounds to show all markers
        fitMapBounds();

        console.log('Map data rendered successfully');
    }


    /**
     * Load study data from the API
     */
    function loadStudyData(studyId) {
        console.log('Loading study data for:', studyId);

        showLoading(true);
        hideError();

        // Make API call to get study map data
        fetch('/vault-integration/wonderdrugs/api/data/' + studyId)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to load study data: ' + response.status);
                }
                return response.json();
            })
            .then(data => {
                console.log('response : ', data);
					const sitesWithColors = data.sites.map(site => {
					  const colorsNew = site.site_status__v
					    .map(status => {
					      const legendMatch = data.legend.find(l => l.status.includes(status));
					      return legendMatch ? legendMatch.color : null;
					    })
					    .filter(Boolean)
					    .join(", ");
					
					  return {
					    ...site,
					    colorsNew
					  };
					});

				data.sites=sitesWithColors;
				console.log('After Study data loaded:', data);
                currentStudyData = data;
                renderMapData(data);
                updateLegend(data.legend);
                showLoading(false);
            })
            .catch(error => {
                console.error('Error loading study data:', error);
                showError('Failed to load study data: ' + error.message);
                showLoading(false);
            });
    }

    /**
     * Add site markers to the map
     */
    function addSiteMarkers(sites) {
		const coordsMap = {};
		console.log("sites : ",sites)
        sites.forEach(function(site) {
            if (site.latitude && site.longitude) {
				 let lat = parseFloat(site["country__vr.latitude__c"]);
        		 let lng = parseFloat(site["country__vr.longitude__c"]);
        		 const key = `${lat.toFixed(6)},${lng.toFixed(6)}`;

	            // If duplicate, add small offset
	            if (coordsMap[key]) {
	                const offset = 5 * coordsMap[key];
	                lat += offset;
	                lng += offset;
	                coordsMap[key]++;
	            } else {
	                coordsMap[key] = 1;
	            }

                var icon = markerIcons[site.statusColor] || markerIcons['#808080'];

                var marker = L.marker([lat,lng], { 
                    icon: icon 
                }).addTo(map);

                // Create popup content
                var popupContent = createSitePopupContent(site);
                marker.bindPopup(popupContent);

                // Add click event
                marker.on('click', function() {
                    console.log('Site clicked:', site.name);
                });
                siteMarkers.push(marker);
            }
        });
        console.log('Added', siteMarkers.length, 'site markers');
    }

    /**
     * Add country markers to the map
     */
    function addCountryMarkers(sites) {
		console.log("sites",sites)
		 const coordsMap = {};
        sites.forEach(function(site) {
            if (site["country__vr.latitude__c"] && site["country__vr.latitude__c"]) {
				 let lat = parseFloat(site["country__vr.latitude__c"]);
        		 let lng = parseFloat(site["country__vr.longitude__c"]);
        		 const key = `${lat.toFixed(6)},${lng.toFixed(6)}`;
        		  if (coordsMap[key]) {
	                const offset = 0.0001 * coordsMap[key];
	                lat += offset;
	                lng += offset;
	                coordsMap[key]++;
	            } else {
	                coordsMap[key] = 1;
	            }
                var marker = L.marker([lat, lng], {
                    icon: createCountryIcon(site.colorsNew,site.name__v)
                }).addTo(map);

                // Create popup content
                var popupContent = createCountryPopupContent(site);
                marker.bindPopup(popupContent);

                // Add click event
                marker.on('click', function() {
                    console.log('Country clicked:', site);
                    showCountryDetails(site);
                });
                countryMarkers.push(marker);
            }
        });
        console.log('Added', countryMarkers.length, 'country markers');
    }

    /**
     * Create popup content for site markers
     */
    function createSitePopupContent(site) {
        var content = '<div class="site-popup">';
        content += '<h6><strong>' + site.name + '</strong></h6>';
        content += '<p><strong>Site Number:</strong> ' + (site.siteNumber || 'N/A') + '</p>';
        content += '<p><strong>Status:</strong> <span style="color: ' + site.statusColor + '; font-weight: bold;">' + site.siteStatus + '</span></p>';

        if (site.principalInvestigator) {
            content += '<p><strong>Principal Investigator:</strong> ' + site.principalInvestigator + '</p>';
        }

        if (site.plannedEnrollment || site.actualEnrollment) {
            content += '<p><strong>Enrollment:</strong> ' + 
                      (site.actualEnrollment || 0) + ' / ' + 
                      (site.plannedEnrollment || 0) + '</p>';
        }
        if (site.activationDate) {
            content += '<p><strong>Activation Date:</strong> ' + new Date(site.activationDate).toLocaleDateString() + '</p>';
        }
        content += '</div>';
        return content;
    }

    /**
     * Create popup content for country markers
     */
    function createCountryPopupContent(site) {
    var content = '<div class="country-popup">';
    content += '<h6><strong>' + (site["study_country__vr.name__v"] || "N/A") + '</strong></h6>';

    // Show study country EDC ID
    if (site["study_country__vr.edc_id__v"]) {
        content += '<p><strong>EDC ID:</strong> ' + site["study_country__vr.edc_id__v"] + '</p>';
    }

    // Show study country abbreviation
    if (site["study_country__vr.country_abbreviation__c"]) {
        content += '<p><strong>Abbreviation:</strong> ' + site["study_country__vr.country_abbreviation__c"] + '</p>';
    }

    // Show latitude & longitude
    if (site["country__vr.latitude__c"] && site["country__vr.longitude__c"]) {
        content += '<p><strong>Coordinates:</strong> ' 
                 + site["country__vr.latitude__c"] + ', ' 
                 + site["country__vr.longitude__c"] + '</p>';
    }

    if (site.countryCoordinator) {
        content += '<p><strong>Country Coordinator:</strong> ' + site.countryCoordinator + '</p>';
    }

    if (site.regulatoryStatus) {
        content += '<p><strong>Regulatory Status:</strong> ' + site.regulatoryStatus + '</p>';
    }

    if (site.ethicsApprovalDate) {
        content += '<p><strong>Ethics Approval:</strong> ' + site.ethicsApprovalDate + '</p>';
    }

    content += '<button class="btn btn-sm btn-primary mt-2" onclick="StudyMap.showCountryDetails(\'' 
            + site.country__v + '\')">View Details</button>';
    content += '</div>';

    return content;
}


    /**
     * Show country details (requirement from PPT)
     */
   function showCountryDetails(country) {
    console.log('Showing country details:', country);

    // Create detailed popup or modal
    var modal = '<div class="modal fade" id="countryModal" tabindex="-1">';
    modal += '<div class="modal-dialog modal-lg">';
    modal += '<div class="modal-content">';
    modal += '<div class="modal-header">';
    modal += '<h5 class="modal-title">Country Details: ' + (country["study_country__vr.name__v"]) + '</h5>';
    modal += '<button type="button" class="btn-close" data-bs-dismiss="modal"></button>';
    modal += '</div>';
    modal += '<div class="modal-body">';

    // Country information
    modal += '<div class="row">';
    modal += '<div class="col-md-6">';
    modal += '<h6>Country Information</h6>';
    
    
    if (country["study_country__vr.edc_id__v"]) {
        modal += '<p><strong>EDC ID:</strong> ' + country["study_country__vr.edc_id__v"] + '</p>';
    }
    if (country["study_country__vr.country_abbreviation__c"]) {
        modal += '<p><strong>Abbreviation:</strong> ' + country["study_country__vr.country_abbreviation__c"] + '</p>';
    }
    if (country["country__vr.latitude__c"]) {
        modal += '<p><strong>Latitude:</strong> ' + country["country__vr.latitude__c"] + '</p>';
    }
    if (country["country__vr.longitude__c"]) {
        modal += '<p><strong>Longitude:</strong> ' + country["country__vr.longitude__c"] + '</p>';
    }

    modal += '</div>';

    
    modal += '</div>';

    // Sites table
    if (country.sites && country.sites.length > 0) {
        modal += '<h6 class="mt-3">Sites in this Country</h6>';
        modal += '<div class="table-responsive">';
        modal += '<table class="table table-sm">';
        modal += '<thead><tr><th>Site</th><th>Status</th><th>Enrollment</th><th>PI</th></tr></thead>';
        modal += '<tbody>';

        country.sites.forEach(function(site) {
            modal += '<tr>';
            modal += '<td>' + site.name + '</td>';
            modal += '<td><span style="color: ' + site.statusColor + ';">●</span> ' + site.siteStatus + '</td>';
            modal += '<td>' + (site.actualEnrollment || 0) + '/' + (site.plannedEnrollment || 0) + '</td>';
            modal += '<td>' + (site.principalInvestigator || '-') + '</td>';
            modal += '</tr>';
        });

        modal += '</tbody></table>';
        modal += '</div>';
    }

    modal += '</div>';
    modal += '<div class="modal-footer">';
    modal += '<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';
    modal += '</div>';
    modal += '</div>';
    modal += '</div>';
    modal += '</div>';

    // Remove existing modal and add new one
    var existingModal = document.getElementById('countryModal');
    if (existingModal) {
        existingModal.remove();
    }

    document.body.insertAdjacentHTML('beforeend', modal);

    // Show modal
    var modalElement = new bootstrap.Modal(document.getElementById('countryModal'));
    modalElement.show();
}


    /**
     * Update legend
     */
    function updateLegend(legend) {
        var legendContainer = document.getElementById('legendContainer');
        legendContainer.innerHTML = '';

        legend.forEach(function(item) {
            var legendItem = document.createElement('div');
            legendItem.className = 'legend-item';
            legendItem.innerHTML = '<div class="legend-color" style="background-color: ' + item.color + ';"></div>' +
                                   '<span>' + item.label + '</span>';
            legendContainer.appendChild(legendItem);
        });
    }

    /**
     * Clear all markers from the map
     */
    function clearMarkers() {
        siteMarkers.forEach(function(marker) {
            map.removeLayer(marker);
        });
        countryMarkers.forEach(function(marker) {
            map.removeLayer(marker);
        });

        siteMarkers = [];
        countryMarkers = [];
    }

    /**
     * Toggle site markers visibility
     */
    function toggleSiteMarkers(visible) {
        siteMarkers.forEach(function(marker) {
            if (visible) {
                marker.addTo(map);
            } else {
                map.removeLayer(marker);
            }
        });
    }

    /**
     * Toggle country markers visibility
     */
    function toggleCountryMarkers(visible) {
        countryMarkers.forEach(function(marker) {
            if (visible) {
                marker.addTo(map);
            } else {
                map.removeLayer(marker);
            }
        });
    }

    /**
     * Fit map bounds to show all markers
     */
    function fitMapBounds() {
        var allMarkers = [...siteMarkers, ...countryMarkers];
        if (allMarkers.length > 0) {
            var group = new L.featureGroup(allMarkers);
            map.fitBounds(group.getBounds().pad(0.1));
        }
    }

    /**
     * Show/hide loading indicator
     */
    function showLoading(show) {
        document.getElementById('loadingContainer').style.display = show ? 'block' : 'none';
    }

    /**
     * Show error message
     */
    function showError(message) {
        var errorContainer = document.getElementById('errorContainer');
        errorContainer.textContent = message;
        errorContainer.style.display = 'block';
    }

    /**
     * Hide error message
     */
    function hideError() {
        document.getElementById('errorContainer').style.display = 'none';
    }

    // Public API
    return {
        init: init,
        loadStudyData: loadStudyData,
        showCountryDetails: showCountryDetails
    };

})();