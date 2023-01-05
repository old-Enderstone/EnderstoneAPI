drop table `Property`;
drop table `SystemProperty`;

create table `propertyIdentifiers` (
    `label` varchar(128) PRIMARY KEY NOT NULL,
    `id` int NOT NULL AUTO_INCREMENT
);

create table `property` (
    `id` int NOT NULL,
    `uId` varchar(36),
    `value` varchar(65000),
    PRIMARY KEY(`id`, `uId`)
);

create table `arrayProperty` (
    `id` int NOT NULL,
    `uId` varchar(36),
    `index` int,
    `value` varchar(65000),
    PRIMARY KEY(`id`, `uId`)
);

ALTER TABLE `propertyIdentifiers` ADD FOREIGN KEY (`id`) REFERENCES `property` (`id`);
ALTER TABLE `propertyIdentifiers` ADD FOREIGN KEY (`id`) REFERENCES `arrayProperty` (`id`);

ALTER TABLE `property` ADD FOREIGN KEY (`uId`) REFERENCES `Player` (`uId`);
ALTER TABLE `arrayProperty` ADD FOREIGN KEY (`uId`) REFERENCES `Player` (`uId`);