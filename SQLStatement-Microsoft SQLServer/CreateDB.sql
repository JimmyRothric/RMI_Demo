create database BankManagement
on
(
	name = 'HotelManagementDBData',
	filename = 'D:\database\BankManagement_data.mdf',
	size = 5MB,
	maxsize = 10MB,
	filegrowth = 1MB
)
log on
(
	name = 'HotelManagementDBLog',
	filename = 'D:\database\BankManagement_log.ldf',
	size = 5MB,
	maxsize = 10MB,
	filegrowth = 1MB
)