CREATE TABLE `Property` (
    `uId` varchar(36) NOT NULL,
    `property` varchar(128) NOT NULL,
    `value` varchar(1024),
    PRIMARY KEY (`uId`, `property`)
);

CREATE TABLE `Player` (
    `uId` varchar(36) PRIMARY KEY NOT NULL,
    `lastKnownName` varchar(16)
);

ALTER TABLE `Property` ADD FOREIGN KEY (`uId`) REFERENCES `Player` (`uId`);

CREATE TABLE `SystemProperty` (
    `property` varchar(128) PRIMARY KEY NOT NULL,
    `value` varchar(1024)
);

CREATE TABLE `bundles` (
    `bId` varchar(36) NOT NULL,
    `tKey` varchar(128) NOT NULL,
PRIMARY KEY(`bId`, `tKey`)
);

CREATE TABLE `translations` (
    `tKey` varchar(128) NOT NULL,
    `tLocale` varchar(5) NOT NULL,
    `tValue` varchar(1024),
    PRIMARY KEY (`tKey`, `tLocale`)
);