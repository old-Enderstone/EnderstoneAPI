drop table `Property`;
drop table `SystemProperty`;

create table `propertyIdentifiers` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT,
    `label` varchar(128) NOT NULL,
    PRIMARY KEY(`id`, `label`)
);

create table `property` (
    `id` int UNSIGNED NOT NULL,
    `uId` varchar(36),
    `value` varchar(16000),
    PRIMARY KEY(`id`, `uId`)
);

create table `arrayProperty` (
    `id` int UNSIGNED NOT NULL,
    `uId` varchar(36),
    `index` int,
    `value` varchar(16000),
    PRIMARY KEY(`id`, `uId`)
);

ALTER TABLE `property` ADD FOREIGN KEY (`id`) REFERENCES `propertyIdentifiers` (`id`);
ALTER TABLE `arrayProperty` ADD FOREIGN KEY (`id`) REFERENCES `propertyIdentifiers` (`id`);
CREATE UNIQUE INDEX idx_key ON `bundles`(`bId`);