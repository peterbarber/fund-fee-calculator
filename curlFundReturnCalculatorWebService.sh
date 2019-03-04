echo "fundfeecalculator"
curl --header "Content-type: application/json" \
--request GET \
'http://localhost:8080/services/fundfeecalculator/byAge?initial=100000&contribution=10000&startAge=0&endAge=20&fees=2&yield=8'

echo "compareSuperannuationFunds"
curl --header "Content-type: application/json" \
--request POST \
--data '{"initialInvestmentAmount":"20000", "salary":60000, "contribution":0, "startAge":30, "endAge":67, "funds":[{"name":"Low Cost Fund", "percentageFeePa":0.04, "fixedFeePa":78, "yieldPa":8}, {"name":"Your Fund", "percentageFeePa":1.2, "fixedFeePa":0, "yieldPa":8}]}' \
'http://localhost:8080/services/fundreturncalculator/compareSuperannuationFunds'

echo "advisercostcalculator"
curl --header "Content-type: application/json" \
--request POST \
--data '{"initialInvestment":"200000", "yearlyInvestment":0, "years":30,  "funds":[{"name":"zero fees", "percentageFeePa":0.00, "fixedFeePa":0, "yieldPa":8}, {"name":"flat fees", "percentageFeePa":0.00, "fixedFeePa":500, "yieldPa":8}, {"name":"asset fees", "percentageFeePa":2.0, "fixedFeePa":0, "yieldPa":8}]}' \
'http://localhost:8080/services/advisercostcalculator/compare'


