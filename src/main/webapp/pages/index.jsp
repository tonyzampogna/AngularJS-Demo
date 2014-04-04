<!DOCTYPE html>
<html lang="en" ng-app="admin">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<!-- jQuery -->
	<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
	
	<!-- Bootstrap -->
	<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	
	<!-- AngularJS -->
	<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.15/angular.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.15/angular-route.min.js"></script>
	
	<!-- AngularJS -->
	<script src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.10.0.js"></script>

	<!-- Our Application -->
	<script src="scripts/app.js"></script>

</head>
<body>
	<div class="container">
		<div ng-view></div>
		<br/><br/><br/><br/>
	</div>		
</body>
</html>