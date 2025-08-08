EXPLAIN ANALYZE
SELECT tracker_schema.integrations.id, tracker_schema.integrations.name, tracker_schema.integrations.url, tracker_schema.projects.name AS project_name
FROM tracker_schema.integrations
INNER JOIN tracker_schema.projects
ON tracker_schema.integrations.id = tracker_schema.projects.id;
