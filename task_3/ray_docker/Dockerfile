FROM rayproject/ray:2.3.1-py310-cpu
EXPOSE 8265 10001
ENTRYPOINT ["ray", "start"]
CMD ["--head", "--dashboard-host", "0.0.0.0", "--dashboard-port", "8265", "--block"]