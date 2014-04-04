var app = angular.module("admin", ["ngRoute", "ui.bootstrap"]);

app.config(['$routeProvider', function($routeProvider) {
			$routeProvider.when('/', {
				templateUrl: 'partials/users-manage.html',
				controller: 'ManageUsersCtrl'
			}).
			when('/user/:userId', {
				templateUrl: 'partials/user-detail.html',
				controller: 'UserDetailCtrl'
			}).
			otherwise({
				redirectTo: '/users'
			});
	}]);

app.controller("ManageUsersCtrl", function($scope) {
	$scope.maxSize = 5; // Number of visible pages.
	$scope.itemsPerPage = 5; // Number of items per pages.
	$scope.currentPage = 1;
	$scope.setPage = function(pageNumber) {
		$scope.currentPage = pageNumber;
	};
	
	
	$scope.addUser = function(newUser) {
		$.ajax({
			method: "POST",
			url: "services/user",
			contentType: "application/json",
			data: JSON.stringify(newUser),
			success: function(data) {
				$scope.getUsers();
			}
		});
	},
	
	$scope.getUser = function(selectedUsername) {
		$.ajax({
			url: "services/user/" + selectedUsername,
			contentType: "application/json",
			success: function(data) {
				$scope.setUsers(data);
			}
		});
	},
	
	$scope.getUsers = function() {
		$.ajax({
			url: "services/users",
			contentType: "application/json",
			success: function(data) {
				$scope.setUsers(data);
			}
		});
	},
	
	$scope.searchUsers = function(searchString) {
		var query = { q: searchString };
		$.ajax({
			url: "services/users",
			data: query,
			success: function(data) {
				$scope.setUsers(data);
			}
		});
	},
	
	$scope.deleteUser = function(selectedUser) {
		delete selectedUser["$$hashKey"];
		$.ajax({
			method: "DELETE",
			url: "services/user",
			contentType: "application/json",
			data: JSON.stringify(selectedUser),
			success: function(data) {
				$scope.getUsers();
			}
		});
		
		return false;
	},
	
	$scope.setUsers = function(users) {
		$scope.$apply(function() {
			$scope.users = users;
			$scope.totalPaginationItems = users.length;
		});
	},
	
	$scope.users = $scope.getUsers()

});




app.controller("UserDetailCtrl", function($scope, $routeParams, $location) {
	$scope.updateUser = function(user) {
		$.ajax({
			method: "PUT",
			url: "services/user",
			contentType: "application/json",
			data: JSON.stringify(user),
			success: function(data) {
				$scope.$apply(function() {
					$location.path('/');
				});
			}
		});
	},
	
	$scope.initUser = function() {
		$.ajax({
			url: "services/user/" + $routeParams['userId'],
			contentType: "application/json",
			success: function(data) {
				$scope.$apply(function() {
					$scope.user = data;
				});
			}
		});
	},
	$scope.user = $scope.initUser()
});



