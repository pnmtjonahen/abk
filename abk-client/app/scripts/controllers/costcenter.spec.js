'use strict';

describe('Controller: CostCenterController', function () {

   // load the controller's module
   beforeEach(module('abkServices'));
   beforeEach(module('abkClientApp'));

   var costcenterCotroller,
        $httpBackend,
        createController,
        currentDateMock;

   beforeEach(inject(function (_$httpBackend_, $controller, $rootScope) {
      currentDateMock = {
         current: function () {
            return new Date(2014, 11, 19, 0, 0, 0, 0);
         },
         range: function () {
            return {'start': new Date(2014, 10, 19, 0, 0, 0, 0), 'end': new Date(2014, 11, 19, 0, 0, 0, 0)};
         }
      };
      $httpBackend = _$httpBackend_;
      $httpBackend.when('GET', 'http://localhost:3002/costcenters?expand=3').
           respond({list: [{"id": 51, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/51"}, "filter": ".*SALARIS.*", "name": "Salaris", "costcenters": [{"id": 69, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/69"}, "filter": "A new Filter", "name": "New Name", "parent": {"id": 51, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/51"}}, "costcenters": []}]}, {"id": 52, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/52"}, "filter": "", "name": "Huis", "costcenters": []}, {"id": 53, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/53"}, "filter": ".*SPAARHYPOTHEEK.*", "name": "Hypotheek", "costcenters": []}, {"id": 54, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/54"}, "filter": ".*WONINGFONDS.*", "name": "Woningfonds", "costcenters": []}, {"id": 55, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/55"}, "filter": "", "name": "Sparen", "costcenters": []}, {"id": 56, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/56"}, "filter": ".*Z 593-87452.*", "name": "Oranje rekening", "costcenters": []}, {"id": 57, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/57"}, "filter": "", "name": "Sport", "costcenters": []}, {"id": 58, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/58"}, "filter": ".*DIPNOI.*", "name": "Duikteam DIPNOI", "costcenters": []}, {"id": 59, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/59"}, "filter": ".*Body Dynamics.*", "name": "Body Dynamics", "costcenters": []}, {"id": 60, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/60"}, "filter": "", "name": "Kas opname", "costcenters": []}, {"id": 61, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/61"}, "filter": ".*CHIPKNIP.*", "name": "OPL. CHIPKNIP", "costcenters": []}, {"id": 62, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/62"}, "filter": "", "name": "OV", "costcenters": []}, {"id": 63, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/63"}, "filter": ".*Kaartnummer:3528020061568163.*", "name": "Mijn OV-chip", "costcenters": []}, {"id": 64, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/64"}, "filter": ".*Kaartnummer:3528020055930650.*", "name": "Amber/Veerle", "costcenters": []}, {"id": 65, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/65"}, "filter": ".*Kaartnummer:3528020038133489.*", "name": "Amber", "costcenters": []}, {"id": 66, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/66"}, "filter": ".*", "name": "Totaal", "costcenters": []}, {"id": 67, "meta": {"href": "http://localhost:8080/abk-backend/rest/costcenters/67"}, "filter": "", "name": "Verzekering", "costcenters": []}]});
      $httpBackend.when('GET', 'views/home.html').respond({});
      createController = function () {
         $httpBackend.expectGET('http://localhost:3002/costcenters?expand=3');
         costcenterCotroller = $controller('CostCenterController', {currentDate: currentDateMock});
         $httpBackend.flush();
         $rootScope.$apply();

      };
   }));

   afterEach(function () {
      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
   });

   it('should have a non empty data collection', function (done) {
      createController();
      expect(costcenterCotroller.data.length).toBe(17);
      done();
   });
   it('should have a costcenter collection', function (done) {
      createController();
      expect(costcenterCotroller.costcenters.length).toBe(17);
      done();
   });


});
