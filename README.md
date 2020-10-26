# CamundaTrainingEngine
Projekt na potrzeby szkolenia "Camunda dla developerów"

# Jak Zacząć:
docker-compose.yml zawiera wszystkie potrzebne obrazy + konfiguracje środowiska. odpalamy:

``docker-compose -f docker-compose.yml up``

następnie uruchamiamy aplikację np.: z poziomu IDE

W ramach odpalonego środowiska dostępne są serwisy:
* http://localhost:8080 - Camunda Cockpit
* http://localhost:9090 - panel administracyjny Keycloak
* http://localhost:8025 - mailhog
